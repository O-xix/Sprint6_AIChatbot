package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@org.springframework.context.annotation.Bean
	org.springframework.boot.CommandLineRunner testLlm(com.example.demo.config.service.LlmService llm) {
		return args -> {
			// Test the LLM service here
			String response = llm.reply(java.util.List.of(
				new com.example.demo.config.service.LlmService.ChatMessages("user", "Hello, how are you?")
			));
			System.out.println("LLM Response: " + response);
		};
	}

}
