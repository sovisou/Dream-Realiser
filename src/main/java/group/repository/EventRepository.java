package group.repository;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import group.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class EventRepository {
    private final DynamoDBMapper dynamoDBMapper;

    @Autowired
    public EventRepository(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    public Event save(Event event) {
        if (event.getId() == null) {
            event.setId(UUID.randomUUID().toString()); // Генерація ID
        }
        dynamoDBMapper.save(event);
        return event;
    }

    public List<Event> findAll() {
        return dynamoDBMapper.scan(Event.class, new DynamoDBScanExpression());
    }

    public Optional<Event> findById(String id) {
        Event event = dynamoDBMapper.load(Event.class, id);
        return Optional.ofNullable(event);
    }

    public List<Event> findAllById(List<String> eventIds) {
        return eventIds.stream()
                .map(eventId -> dynamoDBMapper.load(Event.class, eventId))
                .collect(Collectors.toList());
    }

    public void delete(Event event) {
        dynamoDBMapper.delete(event);
    }
}
