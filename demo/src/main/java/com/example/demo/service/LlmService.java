package com.example.demo.service;

import java.time.Duration;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class LlmService {
    public record ChatMessage(String role, String content){}

    public record ChatRequest(String model, List<ChatMessage> messages){}
    
    public record Choice(ChatMessage message){}

    public record ChatResponse(List<Choice> choices){}

    private final RestClient restClient;

    private final String model;

    public LlmService(RestClient.Builder builder, @Value("${llm.base-url}") String baseUrl, @Value("${llm.api-key}") String apiKey, @Value("${llm.model}") String model) {
        // Configure the HTTP client with timeouts and API key
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(Duration.ofSeconds(10));
        requestFactory.setReadTimeout(Duration.ofSeconds(60));

        // Build the RestClient with the base URL, timeout, and API key
        this.restClient = builder.baseUrl(baseUrl)
            .requestFactory(requestFactory)
            .defaultHeader("Authorization", "Bearer " + apiKey)
            .build();

        // Store the model name
        this.model = model;
    }

    public String reply(List<ChatMessage> conversation) {
        // Build the request payload
        ChatResponse response = restClient.post()
        .uri("/chat/completions")
        .contentType(MediaType.APPLICATION_JSON)
        .body(new ChatRequest(model, conversation))
        .retrieve()
        .body(ChatResponse.class);

        if (response == null || response.choices == null || response.choices().isEmpty()) {
            throw new IllegalStateException("The model sent an empty reply.");
        }
        return response.choices().get(0).message().content();
    }
}
