package group.service;

import com.stripe.exception.StripeException;
import group.dto.event.CreateEventRequestDto;
import group.dto.event.EventDto;
import group.dto.event.EventDtoWithoutCategoryId;
import group.dto.event.EventResponseDto;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

public interface EventService {
    EventDto createEvent(CreateEventRequestDto dto);

    EventDto updateEvent(String eventId, CreateEventRequestDto dto);

    void deleteEvent(String eventId);

    CreateEventRequestDto convertJsonToDto(String eventJson);

    EventResponseDto getEvent(String eventId);

    List<EventResponseDto> getAllEvents();

    List<EventDtoWithoutCategoryId> getEventsByCategoryId(String categoryId);

    void incrementParticipants(String eventId);

    void incrementDonation(String eventId, Double amount);

    Map<String, Boolean> processDonation(String eventId, String paymentMethodId, String email, String name, double amountInDollars) throws StripeException;

    void syncExistingEvents();

    List<EventDto> findSimilarEvents(List<Double> queryEmbedding, int topK);

}
