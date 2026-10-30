package com.example.demo.config.service;

import java.time.Duration;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;
import org.springframework.http.client.SimpleClientHttpRequestFactory;

@Service
public class LlmService {
    public record ChatMessages(String role, String content) {}
    public record ChatRequest(String model, List<ChatMessages> messages) {}
    public record Choice(ChatMessages message) {}
    public record ChatResponse(List<Choice> choices) {}
    private final RestClient restClient;
    private final String model;
    public LlmService(RestClient.Builder builder, @Value("${llm.model}") String model) {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(Duration.ofSeconds(5));
        requestFactory.setReadTimeout(Duration.ofSeconds(30));
        this.restClient = builder.baseUrl("http://localhost:8080")
            .requestFactory(requestFactory)
            .defaultHeader("Authorization", "Bearer" + apiKey)
            .build();
        this.model = model;
    }
    public String reply(List<ChatMessages> messages) {
        ChatResponse response = restClient.post()
                .uri("/chat/completions")
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ChatRequest(model, messages))
                .retrieve()
                .body(ChatResponse.class);
        if (response == null || response.choices() == null || response.choices().isEmpty()) {
            throw new IllegalStateException("No response from the LLM service: IllegalStateException");
        }
        return response.choices().get(0).message().content();
    }
}