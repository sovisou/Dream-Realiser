package group.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class EmbeddingService {
    private final RestTemplate restTemplate;
    private final String openAiApiKey;

    public EmbeddingService(RestTemplate restTemplate, @Value("${spring.ai.openai.api-key}") String openAiApiKey) {
        this.restTemplate = restTemplate;
        this.openAiApiKey = openAiApiKey;
    }

    public List<Double> generateEmbedding(String text) {
        Map<String, Object> requestBody = Map.of(
                "model", "text-embedding-ada-002",
                "input", text
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(openAiApiKey);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        String url = "https://api.openai.com/v1/embeddings";
        ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);
        Map<String, Object> responseBody = response.getBody();

        if (responseBody == null || !responseBody.containsKey("data")) {
            throw new IllegalArgumentException("Invalid response");
        }
        List<Map<String, Object>> data = (List<Map<String, Object>>) responseBody.get("data");
        Map<String, Object> embeddingObject = data.get(0);
        List<Double> embedding = (List<Double>) embeddingObject.get("embedding");

        return embedding;
    }
}
