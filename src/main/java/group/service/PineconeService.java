package group.service;

import group.model.EventEmbedding;
import group.repository.EventEmbeddingRepository;
import okhttp3.*;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


public class PineconeService {
    private static final Logger logger = LoggerFactory.getLogger(PineconeService.class);

    private final String apiKey;
    private final String pineconeUrl;
    private final OkHttpClient client = new OkHttpClient();
    private final Gson gson = new Gson();
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    @Autowired
    private EventEmbeddingRepository eventEmbeddingRepository;

    public PineconeService(String apiKey, String pineconeUrl) {
        this.apiKey = apiKey;
        this.pineconeUrl = pineconeUrl;
    }


    @PostConstruct
    public void init() {
        loadEmbeddingsToPinecone();
    }


    public void loadEmbeddingsToPinecone() {

        List<EventEmbedding> embeddings = eventEmbeddingRepository.findAll();

        for (EventEmbedding embedding : embeddings) {
            try {
                upsertVectors(embedding.getEventId(), embedding.getEmbedding());
            } catch (IOException e) {
                logger.error("Failed to upsert vector for event ID: " + embedding.getEventId(), e);
            }
        }
    }

    public void upsertVectors(String eventId, List<Double> embedding) throws IOException {
        String jsonBody = gson.toJson(new UpsertRequest(eventId, embedding));

        Request request = new Request.Builder()
                .url(pineconeUrl + "/vectors/upsert")
                .addHeader("Api-Key", apiKey)
                .post(RequestBody.create(jsonBody, JSON))
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Failed to upsert vector: " + response.body().string());
            }
        }
    }

    public List<String> findSimilarEvents(List<Double> queryEmbedding, int topK) throws IOException {
        String jsonBody = gson.toJson(new QueryRequest(queryEmbedding, topK));
        Request request = new Request.Builder()
                .url(pineconeUrl + "/query")
                .addHeader("Api-Key", apiKey)
                .post(RequestBody.create(jsonBody, JSON))
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Failed to query vectors: " + response.body().string());
            }

            QueryResponse queryResponse = gson.fromJson(response.body().string(), QueryResponse.class);
            return queryResponse.getMatches().stream()
                    .map(Match::getId)
                    .collect(Collectors.toList());
        }
    }

    private static class UpsertRequest {
        private final List<Vector> vectors;

        UpsertRequest(String eventId, List<Double> embedding) {
            this.vectors = List.of(new Vector(eventId, embedding));
        }
    }

    private static class Vector {
        private final String id;
        private final List<Double> values;

        Vector(String id, List<Double> values) {
            this.id = id;
            this.values = values;
        }
    }

    private static class QueryRequest {
        private final List<Double> vector;
        private final int topK;

        QueryRequest(List<Double> vector, int topK) {
            this.vector = vector;
            this.topK = topK;
        }
    }

    private static class QueryResponse {
        private List<Match> matches;

        List<Match> getMatches() {
            return matches;
        }
    }

    private static class Match {
        private String id;

        String getId() {
            return id;
        }
    }
}