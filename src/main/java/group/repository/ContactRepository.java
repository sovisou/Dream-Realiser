package group.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import group.model.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class ContactRepository {
    private final DynamoDBMapper dynamoDBMapper;

    @Autowired
    public ContactRepository(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    public Contact save(Contact contact) {
        if (contact.getId() == null) {
            contact.setId(UUID.randomUUID().toString());
        }
        dynamoDBMapper.save(contact);
        return contact;
    }
}
