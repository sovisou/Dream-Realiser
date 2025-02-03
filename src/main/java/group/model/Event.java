package group.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Data;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@DynamoDBTable(tableName = "events")
@Data
public class Event {

    @DynamoDBHashKey(attributeName = "id")
    private String id;

    @DynamoDBAttribute(attributeName = "organizerType")
    private String organizerType;

    @DynamoDBAttribute(attributeName = "organizerName")
    private String organizerName;

    @DynamoDBAttribute(attributeName = "organizerEmail")
    private String organizerEmail;

    @DynamoDBAttribute(attributeName = "phone")
    private String phone;

    @DynamoDBAttribute(attributeName = "organizationLink")
    private String link;

    @DynamoDBAttribute(attributeName = "organizerPhoto")
    private String organizerPhoto;

    @DynamoDBAttribute(attributeName = "title")
    private String title;

    @DynamoDBAttribute(attributeName = "categoryId")
    private String categoryId;

    @DynamoDBAttribute(attributeName = "opportunityType")
    private String opportunityType;

    @DynamoDBAttribute(attributeName = "assistanceType")
    private String assistanceType;

    @DynamoDBAttribute(attributeName = "target")
    private Double target;

    @DynamoDBAttribute(attributeName = "region")
    private String region;

    @DynamoDBAttribute(attributeName = "address")
    private String address;

    @DynamoDBAttribute(attributeName = "startingDate")
    private String startingDate;

    @DynamoDBAttribute(attributeName = "startHour")
    private String startHour;

    @DynamoDBAttribute(attributeName = "startMinute")
    private String startMinute;

    @DynamoDBAttribute(attributeName = "endingDate")
    private String endingDate;

    @DynamoDBAttribute(attributeName = "endHour")
    private String endHour;

    @DynamoDBAttribute(attributeName = "endMinute")
    private String endMinute;

    @DynamoDBAttribute(attributeName = "startPeriod")
    private String startPeriod;

    @DynamoDBAttribute(attributeName = " endPeriod")
    private String endPeriod;

    @DynamoDBAttribute(attributeName = "timeDemands")
    private String timeDemands;

    @DynamoDBAttribute(attributeName = "skills")
    private String skills;

    @DynamoDBAttribute(attributeName = "coverImage")
    private String coverImage;

    @DynamoDBAttribute(attributeName = "description")
    private String description;

    @DynamoDBAttribute(attributeName = "descriptionLink")
    private String descriptionLink;

    @DynamoDBAttribute(attributeName = "document")
    private String document;

    @DynamoDBAttribute(attributeName = "currentProgress")
    private Double currentProgress; // додати йому 0.0

    @DynamoDBAttribute(attributeName = "eventLink")
    private Double eventLink;

}
