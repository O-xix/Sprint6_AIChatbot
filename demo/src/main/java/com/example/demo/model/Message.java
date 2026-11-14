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

    @ManyToOne(optional = false)
    private Conversation conversation;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false)
    private String content;

    private Instant createdAt = Instant.now();

    public Message(Conversation conversation, String role, String content) {
        this.content = content;
        this.conversation = conversation;
        this.role = role;
    }

    // Getters and Setters
}

