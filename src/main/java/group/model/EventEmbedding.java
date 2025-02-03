package group.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Data;

import java.util.List;

@DynamoDBTable(tableName = "embeddings")
@Data
public class EventEmbedding {

    @DynamoDBHashKey(attributeName = "eventId")
    private String eventId;
    @DynamoDBAttribute(attributeName = "embedding")
    private List<Double> embedding;
}
