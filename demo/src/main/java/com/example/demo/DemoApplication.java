package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@org.springframework.context.annotation.Bean 
	org.springframework.boot.CommandLineRunner testLlm(com.example.demo.service.LlmService llm) {
		return args -> System.out.println("Gemini: " + llm.reply(java.util.List.of(new com.example.demo.service.LlmService.ChatMessage("user", "How are you?"))));
	}
}
