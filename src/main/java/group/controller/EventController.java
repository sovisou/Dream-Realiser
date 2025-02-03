package group.controller;

import com.stripe.exception.StripeException;
import group.dto.event.CreateEventRequestDto;
import group.dto.event.EventDto;
import group.dto.event.EventDtoWithoutCategoryId;
import group.dto.event.EventResponseDto;
import group.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public EventDto createEvent(
            @RequestPart("event") String eventJson,
            @RequestPart(value = "coverImageFile", required = false) MultipartFile coverImageFile,
            @RequestPart(value = "organizerPhotoFile", required = false) MultipartFile organizerPhotoFile,
            @RequestPart(value = "documentFile", required = false) MultipartFile documentFile) {

        CreateEventRequestDto dto = eventService.convertJsonToDto(eventJson);
        dto = dto.withFiles(coverImageFile, organizerPhotoFile, documentFile);

        return eventService.createEvent(dto);
    }

    @PutMapping("/{eventId}")
    public EventDto updateEvent(@PathVariable String eventId, @RequestBody CreateEventRequestDto dto) {
        return eventService.updateEvent(eventId, dto);
    }

    @DeleteMapping("/{eventId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEvent(@PathVariable String eventId) {
        eventService.deleteEvent(eventId);
    }

    @GetMapping("/{eventId}")
    public EventResponseDto getEvent(@PathVariable String eventId) {
        return eventService.getEvent(eventId);
    }

    @GetMapping
    public List<EventResponseDto> getAllEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping("/category/{categoryId}")
    public List<EventDtoWithoutCategoryId> getEventsByCategoryId(@PathVariable String categoryId) {
        return eventService.getEventsByCategoryId(categoryId);
    }

    @PutMapping("/{eventId}/join")
    public void joinEvent(@PathVariable String eventId) {
        eventService.incrementParticipants(eventId);
    }

    @PostMapping("/sync-embeddings")
    public ResponseEntity<String> syncEmbeddings() {
        eventService.syncExistingEvents();
        return ResponseEntity.ok("Embeddings synchronization completed.");
    }

//    @PutMapping("/{eventId}/donate")
//    public void donateToEvent(@PathVariable String eventId, @RequestParam Double amount) {
//        eventService.incrementDonation(eventId, amount);
//    }

    @PostMapping("/{id}/donate")
    public Map<String, Boolean> donateToEvent(
            @PathVariable String id,
            @RequestBody Map<String, String> payload
    ) throws StripeException {
        String paymentMethodId = payload.get("paymentMethodId");
        String email = payload.get("email");
        String name = payload.get("name");
        double amountInDollars = Double.parseDouble(payload.get("amount"));

        return eventService.processDonation(id, paymentMethodId, email, name, amountInDollars);
    }
}
