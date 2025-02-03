package group.dto.event;

import org.springframework.web.multipart.MultipartFile;

public record CreateEventRequestDto(String organizerType,
                                    String organizerName,
                                    String organizerEmail,
                                    String phone,
                                    String link,
                                    String organizerPhoto,
                                    String title,
                                    String categoryId,
                                    String opportunityType,
                                    String assistanceType,
                                    Double target,
                                    String region,
                                    String address,
                                    String startingDate,
                                    String startHour,
                                    String startMinute,
                                    String endingDate,
                                    String endHour,
                                    String endMinute,
                                    String startPeriod,
                                    String endPeriod,
                                    String timeDemands,
                                    String skills,
                                    String coverImage,
                                    String description,
                                    String descriptionLink,
                                    String document,
                                    Double currentProgress,

                                    MultipartFile coverImageFile,
                                    MultipartFile organizerPhotoFile,
                                    MultipartFile documentFile
) {
    public CreateEventRequestDto withFiles(MultipartFile coverImageFile, MultipartFile organizerPhotoFile, MultipartFile documentFile) {
        return new CreateEventRequestDto(
                organizerType, organizerName, organizerEmail, phone, link, organizerPhoto, title,
                categoryId, opportunityType, assistanceType, target, region, address,
                startingDate, startHour, startMinute, endingDate, endHour, endMinute,
                startPeriod, endPeriod, timeDemands, skills, coverImage, description, descriptionLink, document,
                currentProgress, coverImageFile, organizerPhotoFile, documentFile
        );
    }
}
