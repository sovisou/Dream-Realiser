package group;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.CognitoUserPoolPostConfirmationEvent;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.PutItemResponse;

import java.util.HashMap;
import java.util.Map;

public class CognitoPostConfirmationHandler implements RequestHandler<CognitoUserPoolPostConfirmationEvent,
        CognitoUserPoolPostConfirmationEvent> {

    private final DynamoDbClient dynamoDbClient;

    public CognitoPostConfirmationHandler() {
        this.dynamoDbClient = DynamoDbClient.create();
    }

    @Override
    public CognitoUserPoolPostConfirmationEvent handleRequest(CognitoUserPoolPostConfirmationEvent event, Context context) {
        String userId = event.getRequest().getUserAttributes().get("sub");
        String email = event.getRequest().getUserAttributes().get("email");

        saveUserToDynamoDB(userId, email);

        return event;
    }

    private void saveUserToDynamoDB(String userId, String email) {
        Map<String, AttributeValue> item = new HashMap<>();
        item.put("userId", AttributeValue.builder().s(userId).build());
        item.put("email", AttributeValue.builder().s(email).build());
        item.put("name", AttributeValue.builder().s("").build());
        item.put("phone", AttributeValue.builder().s("").build());
        item.put("profileImage", AttributeValue.builder().s("").build());

        PutItemRequest putItemRequest = PutItemRequest.builder()
                .tableName("users")
                .item(item)
                .build();

        PutItemResponse response = dynamoDbClient.putItem(putItemRequest);
        System.out.println("User saved: " + response);
    }
}