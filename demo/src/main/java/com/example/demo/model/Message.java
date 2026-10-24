package com.example.demo.model;

import java.time.Instant;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Conversation conversation;

    @Column(nullable = false)
    private String role;

    public Message(Conversation conversation, String role, String content) {
        this.content = content;
        this.conversation = conversation;
        this.role = role;
    }

    // Getters and Setters
}

