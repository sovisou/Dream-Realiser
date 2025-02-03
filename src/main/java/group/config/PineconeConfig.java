package group.config;

import group.service.PineconeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PineconeConfig {

    @Value("${spring.ai.vectorstore.pinecone.api-key}")
    private String apiKey;

    @Value("${spring.ai.vectorstore.pinecone.environment}")
    private String environment;

    @Value("${spring.ai.vectorstore.pinecone.index-name}")
    private String indexName;

    @Value("${spring.ai.vectorstore.pinecone.project-id}")
    private String projectId;

    @Bean
    public PineconeService pineconeService() {
        String pineconeUrl = String.format(
                "https://%s-%s.svc.%s.pinecone.io",
                indexName, projectId, environment
        );
        return new PineconeService(apiKey, pineconeUrl);
    }
}
