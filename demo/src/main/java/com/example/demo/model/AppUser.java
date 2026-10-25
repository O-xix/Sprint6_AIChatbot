package com.example.demo.model;

import java.time.Instant;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="app_users")
@Getter
@Setter
@NoArgsConstructor
public class AppUser {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique=true, nullable=false)
    private String username;

    @Column(nullable=false)
    private String passwordHash;

    @Column(nullable = false)
    private String role = "USER";

    private Instant createdAt = Instant.now();
}
