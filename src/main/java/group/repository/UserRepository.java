package group.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {
//    private final DynamoDBMapper dynamoDBMapper;
//
//    @Autowired
//    public UserRepository(DynamoDBMapper dynamoDBMapper) {
//        this.dynamoDBMapper = dynamoDBMapper;
//    }
//
//    public User save(User user) {
//        if (user.getEmail() == null) {
//            throw new IllegalArgumentException("Email cannot be null");
//        }
//        dynamoDBMapper.save(user);
//        return user;
//    }
//
//    public List<User> findAll() {
//        return dynamoDBMapper.scan(User.class, new DynamoDBScanExpression());
//    }
//
//    public Optional<User> findByEmail(String email) {
//        User user = dynamoDBMapper.load(User.class, email);
//        return Optional.ofNullable(user);
//    }
//
//    public void delete(User user) {
//        dynamoDBMapper.delete(user);
//    }
}
