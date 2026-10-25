package com.example.demo.repo;

import java.util.List;   
import java.time.Instant;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.AppUser;
import com.example.demo.model.Conversation;
import com.example.demo.model.Message;

public interface MessageRepo extends JpaRepository<Message, Long> {

    List<Message> findByConversationOrderByCreatedAtAsc(Conversation conversation, Pageable pageable);
    
    long countByConversation_UserAndRoleAndCreatedAtAfter(AppUser user, String role, Instant time);
}
