package com.example.demo.repo;

import java.util.List;   
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.AppUser;
import com.example.demo.model.Conversation;

public interface ConversationRepo extends JpaRepository<Conversation, Long> {
    List<Conversation> findByUserOrderByCreatedAtDesc(AppUser user);

    Optional<Conversation> findByUserAndId(AppUser user, Long id);

    void deleteByUser(AppUser user);
    
}
