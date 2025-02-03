package group.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import group.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class CategoryRepository {
    private final DynamoDBMapper dynamoDBMapper;

    @Autowired
    public CategoryRepository(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    public Category save(Category category) {
        if (category.getId() == null) {
            category.setId(UUID.randomUUID().toString());
        }
        dynamoDBMapper.save(category);
        return category;
    }

    public List<Category> findAll() {
        return dynamoDBMapper.scan(Category.class, new DynamoDBScanExpression());
    }


    public Optional<Category> findById(String categoryId) {
        Category category = dynamoDBMapper.load(Category.class, categoryId);
        return Optional.ofNullable(category);
    }

    public void delete(Category category) {
        dynamoDBMapper.delete(category);
    }
}
