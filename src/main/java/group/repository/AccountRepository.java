package group.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import group.model.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class  AccountRepository {

    private final DynamoDBMapper dynamoDBMapper;

    public Optional<Account> findByUserId(String userId) {
        Account account = dynamoDBMapper.load(Account.class, userId);
        return Optional.ofNullable(account);
    }

    public Account save(Account account) {
        dynamoDBMapper.save(account);
        return account;
    }
}
