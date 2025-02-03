package group.dto.event;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class EventDto {
    private String id;
    private String organizerType;
    private String organizerName;
    private String organizerEmail;
    private String phone;
    private String link;
    private String eventLink;
    private String organizerPhoto;
    private String title;
    private String categoryId;
    private String opportunityType;
    private String assistanceType;
    private Double target;
    private String region;
    private String address;
    private String startingDate;
    private String startHour;
    private String startMinute;
    private String endingDate;
    private String endHour;
    private String endMinute;
    private String startPeriod;
    private String endPeriod;
    private String timeDemands;
    private String skills;
    private String coverImage;
    private String description;
    private String descriptionLink;
    private String document;
    private Double currentProgress = 0.0;
}
