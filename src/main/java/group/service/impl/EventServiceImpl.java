package group.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import group.exception.AssistanceTypeException;
import group.exception.EntityNotFoundException;
import group.service.PineconeService;
import group.dto.event.CreateEventRequestDto;
import group.dto.event.EventDto;
import group.dto.event.EventDtoWithoutCategoryId;
import group.dto.event.EventResponseDto;
import group.enums.AssistanceType;
import group.model.Category;
import group.model.Event;
import group.model.EventEmbedding;
import group.repository.CategoryRepository;
import group.repository.EventEmbeddingRepository;
import group.repository.EventRepository;
import group.service.EventService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import group.mapper.EventMapper;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private static final Logger logger = LoggerFactory.getLogger(EventServiceImpl.class);
    private final ObjectMapper objectMapper;
    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final EventMapper eventMapper;
    private final EventEmbeddingRepository eventEmbeddingRepository;
    private final EmbeddingModel embeddingModel;
    private final PineconeService pineconeService;;

    @Override
    public EventDto createEvent(CreateEventRequestDto dto) {
        Event event = eventMapper.toEntity(dto);
        event = eventRepository.save(event);

        if (event.getDescription() != null) {
            String text = event.getTitle() + " " + event.getDescription();
            float[] floatEmbedding = embeddingModel.embed(text);

            List<Double> embedding = new ArrayList<>();
            for (float value : floatEmbedding) {
                embedding.add((double) value);
            }

            EventEmbedding eventEmbedding = new EventEmbedding();
            eventEmbedding.setEventId(event.getId());
            eventEmbedding.setEmbedding(embedding);
            eventEmbeddingRepository.save(eventEmbedding);
        }

        return eventMapper.toDto(event);
    }

    @Override
    public EventDto updateEvent(String eventId, CreateEventRequestDto dto) {
        Event existingEvent = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event not found" + eventId));
        eventMapper.updateEventFromDto(dto, existingEvent);
        existingEvent = eventRepository.save(existingEvent);

        String text = existingEvent.getTitle() + " " + existingEvent.getDescription();
        float[] floatEmbedding = embeddingModel.embed(text);

        List<Double> embedding = new ArrayList<>();
        for (float value : floatEmbedding) {
            embedding.add((double) value);
        }

        EventEmbedding eventEmbedding = eventEmbeddingRepository.findByEventId(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Embedding not found" + eventId));
        eventEmbedding.setEmbedding(embedding);
        eventEmbeddingRepository.save(eventEmbedding);

        return eventMapper.toDto(existingEvent);
    }

    @Override
    public CreateEventRequestDto convertJsonToDto(String eventJson) {
        try {
            return objectMapper.readValue(eventJson, CreateEventRequestDto.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to convert JSON to DTO", e);
        }
    }

    @Override
    public void deleteEvent(String eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event not found" + eventId));
        eventRepository.delete(event);
    }

    @Override
    public EventResponseDto getEvent(String eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event not found" + eventId));

        Category category = categoryRepository.findById(event.getCategoryId())
                .orElse(null);

        return eventMapper.toResponseDto(event, category);
    }

    @Override
    public List<EventResponseDto> getAllEvents() {
        List<Event> events = eventRepository.findAll();
        return events.stream()
                .map(event -> {
                    Category category = categoryRepository.findById(event.getCategoryId()).orElse(null);
                    return eventMapper.toResponseDto(event, category);
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<EventDtoWithoutCategoryId> getEventsByCategoryId(String categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (category.isEmpty()) {
            throw new EntityNotFoundException("Category not found with id: " + categoryId);
        }
        List<Event> allEvents = eventRepository.findAll();
        return allEvents.stream()
                .filter(event -> categoryId.equals(event.getCategoryId()))
                .map(eventMapper::toDtoWithoutCategoryId)
                .collect(Collectors.toList());
    }

    @Override
    public void incrementParticipants(String eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event not found" + eventId));
        if (AssistanceType.VOLUNTEERING.name().equals(event.getAssistanceType())) {
            event.setCurrentProgress(event.getCurrentProgress() + 1);
            eventRepository.save(event);
        } else {
            throw new AssistanceTypeException("This event does not allow participants.");
        }
    }

    @Override
    public void incrementDonation(String eventId, Double amount) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event found" + eventId));
        if (AssistanceType.DONATION.name().equals(event.getAssistanceType())) {
            event.setCurrentProgress(event.getCurrentProgress() + amount);
            eventRepository.save(event);
        } else {
            throw new AssistanceTypeException("This event does not accept donations.");
        }
    }

    @Override
    public Map<String, Boolean> processDonation(String eventId, String paymentMethodId, String email, String name, double amountInDollars) throws StripeException {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event not found" + eventId));

        if (!AssistanceType.DONATION.name().equals(event.getAssistanceType())) {
            throw new AssistanceTypeException("This event does not accept donations.");
        }
        long amountInCents = Math.round(amountInDollars * 100);

        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(amountInCents)
                .setCurrency("usd")
                .setPaymentMethod(paymentMethodId)
                .setReceiptEmail(email)
                .setDescription("Payment from " + name)
                .setConfirm(true)
                .setReturnUrl("https://dewvdtfd5m.execute-api.eu-north-1.amazonaws.com/dev/events")
                .build();

        PaymentIntent paymentIntent = PaymentIntent.create(params);

        if ("succeeded".equals(paymentIntent.getStatus())) {
            event.setCurrentProgress(event.getCurrentProgress() + amountInDollars);
            eventRepository.save(event);
        }

        Map<String, Boolean> response = new HashMap<>();
        response.put("success", "succeeded".equals(paymentIntent.getStatus()));
        return response;
    }

    @Override
    public List<EventDto> findSimilarEvents(List<Double> queryEmbedding, int topK) {
        try {
            List<String> eventIds = pineconeService.findSimilarEvents(queryEmbedding, topK);

            return eventRepository.findAllById(eventIds).stream()
                    .map(event -> {
                        EventDto eventDto = eventMapper.toDto(event);
                        eventDto.setEventLink("https://martachobaniuk.github.io/team_project/#/explore/" + event.getId());
                        return eventDto;
                    })
                    .collect(Collectors.toList());
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }


    @Override
    public void syncExistingEvents() {
        List<Event> events = eventRepository.findAll();
        for (Event event : events) {
            if (!eventEmbeddingRepository.existsByEventId(event.getId())) {
                String text = event.getTitle() + " " + event.getDescription();
                float[] floatEmbedding = embeddingModel.embed(text);
                List<Double> embedding = new ArrayList<>();
                for (float value : floatEmbedding) {
                    embedding.add((double) value);
                }

                EventEmbedding eventEmbedding = new EventEmbedding();
                eventEmbedding.setEventId(event.getId());
                eventEmbedding.setEmbedding(embedding);
                eventEmbeddingRepository.save(eventEmbedding);
            }
        }
    }
}

