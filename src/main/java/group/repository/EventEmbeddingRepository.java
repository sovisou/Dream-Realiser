package group.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import group.model.EventEmbedding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class EventEmbeddingRepository {
    private final DynamoDBMapper dynamoDBMapper;

    @Autowired
    public EventEmbeddingRepository(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    public void save(EventEmbedding eventEmbedding) {
        dynamoDBMapper.save(eventEmbedding);
    }

    public Optional<EventEmbedding> findByEventId(String eventId) {
        EventEmbedding eventEmbedding = dynamoDBMapper.load(EventEmbedding.class, eventId);
        return Optional.ofNullable(eventEmbedding);
    }

    public void delete(String eventId) {
        EventEmbedding eventEmbedding = dynamoDBMapper.load(EventEmbedding.class, eventId);
        if (eventEmbedding != null) {
            dynamoDBMapper.delete(eventEmbedding);
        }
    }

    public boolean existsByEventId(String eventId) {
        EventEmbedding eventEmbedding = dynamoDBMapper.load(EventEmbedding.class, eventId);
        return eventEmbedding != null;
    }

   public List<EventEmbedding> findAll() {
        return dynamoDBMapper.scan(EventEmbedding.class, new DynamoDBScanExpression());
    }
}
