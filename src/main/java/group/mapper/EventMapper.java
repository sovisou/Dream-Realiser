package group.mapper;

import group.dto.event.CreateEventRequestDto;
import group.dto.event.EventDto;
import group.dto.event.EventDtoWithoutCategoryId;
import group.dto.event.EventResponseDto;
import group.model.Category;
import group.model.Event;
import group.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class EventMapper {

    private final FileStorageService fileStorageService;

    public Event toEntity(CreateEventRequestDto dto) {
        String coverImageUrl = null;
        String organizerPhotoUrl = null;
        String documentUrl = null;

        try {
        if (dto.coverImageFile() != null && !dto.coverImageFile().isEmpty()) {
            coverImageUrl = fileStorageService.uploadFile(dto.coverImageFile());
        }
        if (dto.organizerPhotoFile() != null && !dto.organizerPhotoFile().isEmpty()) {
            organizerPhotoUrl = fileStorageService.uploadFile(dto.organizerPhotoFile());
        }
        if (dto.documentFile() != null && !dto.documentFile().isEmpty()) {
            documentUrl = fileStorageService.uploadFile(dto.documentFile());
        } } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Event event = new Event();
        event.setOrganizerType(dto.organizerType());
        event.setOrganizerName(dto.organizerName());
        event.setOrganizerEmail(dto.organizerEmail());
        event.setPhone(dto.phone());
        event.setLink(dto.link());
        event.setOrganizerPhoto(organizerPhotoUrl);
        event.setTitle(dto.title());
        event.setCategoryId(dto.categoryId());
        event.setOpportunityType(dto.opportunityType());
        event.setAssistanceType(dto.assistanceType());
        event.setTarget(dto.target());
        event.setRegion(dto.region());
        event.setAddress(dto.address());
        event.setStartingDate(dto.startingDate());
        event.setStartHour(dto.startHour());
        event.setStartMinute(dto.startMinute());
        event.setEndingDate(dto.endingDate());
        event.setEndHour(dto.endHour());
        event.setEndMinute(dto.endMinute());
        event.setStartPeriod(dto.startPeriod());
        event.setEndPeriod(dto.endPeriod());
        event.setTimeDemands(dto.timeDemands());
        event.setSkills(dto.skills());
        event.setCoverImage(coverImageUrl);
        event.setDescription(dto.description());
        event.setDescriptionLink(dto.descriptionLink());
        event.setDocument(documentUrl);
        event.setCurrentProgress(dto.currentProgress());
        return event;
    }

    public EventDto toDto(Event event) {
        EventDto dto = new EventDto();
        dto.setId(event.getId());
        dto.setOrganizerType(event.getOrganizerType());
        dto.setOrganizerName(event.getOrganizerName());
        dto.setOrganizerEmail(event.getOrganizerEmail());
        dto.setPhone(event.getPhone());
        dto.setLink(event.getLink());
        dto.setOrganizerPhoto(event.getOrganizerPhoto());
        dto.setTitle(event.getTitle());
        dto.setCategoryId(event.getCategoryId());
        dto.setOpportunityType(event.getOpportunityType());
        dto.setAssistanceType(event.getAssistanceType());
        dto.setTarget(event.getTarget());
        dto.setRegion(event.getRegion());
        dto.setAddress(event.getAddress());
        dto.setStartingDate(event.getStartingDate());
        dto.setStartHour(event.getStartHour());
        dto.setStartMinute(event.getStartMinute());
        dto.setEndingDate(event.getEndingDate());
        dto.setEndHour(event.getEndHour());
        dto.setEndMinute(event.getEndMinute());
        dto.setStartPeriod(event.getStartPeriod());
        dto.setEndPeriod(event.getEndPeriod());
        dto.setTimeDemands(event.getTimeDemands());
        dto.setSkills(event.getSkills());
        dto.setCoverImage(event.getCoverImage());
        dto.setDescription(event.getDescription());
        dto.setDescriptionLink(event.getDescriptionLink());
        dto.setDocument(event.getDocument());
        dto.setCurrentProgress(event.getCurrentProgress());
        return dto;
    }

    public EventResponseDto toResponseDto(Event event, Category category) {
        EventResponseDto dto = new EventResponseDto();
        dto.setId(event.getId());
        dto.setOrganizerType(event.getOrganizerType());
        dto.setOrganizerName(event.getOrganizerName());
        dto.setOrganizerEmail(event.getOrganizerEmail());
        dto.setPhone(event.getPhone());
        dto.setLink(event.getLink());
        dto.setOrganizerPhoto(event.getOrganizerPhoto());
        dto.setTitle(event.getTitle());
        dto.setCategoryName(category != null ? category.getName() : null);
        dto.setCategoryId(event.getCategoryId());
        dto.setOpportunityType(event.getOpportunityType());
        dto.setAssistanceType(event.getAssistanceType());
        dto.setTarget(event.getTarget());
        dto.setRegion(event.getRegion());
        dto.setAddress(event.getAddress());
        dto.setStartingDate(event.getStartingDate());
        dto.setStartHour(event.getStartHour());
        dto.setStartMinute(event.getStartMinute());
        dto.setEndingDate(event.getEndingDate());
        dto.setEndHour(event.getEndHour());
        dto.setEndMinute(event.getEndMinute());
        dto.setStartPeriod(event.getStartPeriod());
        dto.setEndPeriod(event.getEndPeriod());
        dto.setTimeDemands(event.getTimeDemands());
        dto.setSkills(event.getSkills());
        dto.setCoverImage(event.getCoverImage());
        dto.setDescription(event.getDescription());
        dto.setDescriptionLink(event.getDescriptionLink());
        dto.setDocument(event.getDocument());
        dto.setCurrentProgress(event.getCurrentProgress());
        return dto;
    }

    public EventDtoWithoutCategoryId toDtoWithoutCategoryId(Event event) {
        return new EventDtoWithoutCategoryId(
                event.getId(),
                event.getOrganizerType(),
                event.getOrganizerName(),
                event.getOrganizerEmail(),
                event.getPhone(),
                event.getLink(),
                event.getOrganizerPhoto(),
                event.getTitle(),
                event.getOpportunityType(),
                event.getAssistanceType(),
                event.getTarget(),
                event.getRegion(),
                event.getAddress(),
                event.getStartingDate(),
                event.getStartHour(),
                event.getStartMinute(),
                event.getEndingDate(),
                event.getEndHour(),
                event.getEndMinute(),
                event.getStartPeriod(),
                event.getEndPeriod(),
                event.getTimeDemands(),
                event.getSkills(),
                event.getCoverImage(),
                event.getDescription(),
                event.getDescriptionLink(),
                event.getDocument(),
                event.getCurrentProgress()
        );
    }

    public void updateEventFromDto(CreateEventRequestDto dto, Event event) {
        event.setOrganizerType(dto.organizerType());
        event.setOrganizerName(dto.organizerName());
        event.setOrganizerEmail(dto.organizerEmail());
        event.setPhone(dto.phone());
        event.setLink(dto.link());
        event.setOrganizerPhoto(dto.organizerPhoto());
        event.setTitle(dto.title());
        event.setCategoryId(dto.categoryId());
        event.setOpportunityType(dto.opportunityType());
        event.setAssistanceType(dto.assistanceType());
        event.setTarget(dto.target());
        event.setRegion(dto.region());
        event.setAddress(dto.address());
        event.setStartingDate(dto.startingDate());
        event.setStartHour(dto.startHour());
        event.setStartMinute(dto.startMinute());
        event.setEndingDate(dto.endingDate());
        event.setEndHour(dto.endHour());
        event.setEndMinute(dto.endMinute());
        event.setStartPeriod(dto.startPeriod());
        event.setEndPeriod(dto.endPeriod());
        event.setTimeDemands(dto.timeDemands());
        event.setSkills(dto.skills());
        event.setCoverImage(dto.coverImage());
        event.setDescription(dto.description());
        event.setDescriptionLink(dto.descriptionLink());
        event.setDocument(dto.document());
        event.setCurrentProgress(dto.currentProgress());
    }
}
