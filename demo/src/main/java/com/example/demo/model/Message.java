package com.example.demo.model;

import java.time.Instant;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="messages")
@Getter
@Setter
@NoArgsConstructor
public class Message {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(optional=false)
    private Conversation conversation;
    
    @Column(nullable=false)
    private String role;
    
    @Column(nullable=false, columnDefinition = "TEXT")
    private String content;
    
    @Column(nullable=false)
    private Instant createdAt = Instant.now();
    
    public Message(Conversation conversation, String role, String content) {
        this.conversation = conversation;
        this.role = role;
        this.content = content;
    }
}