package com.example.demo.repo;

import java.util.List;
import java.time.Instant;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.AppUser;
import com.example.demo.model.Conversation;
import com.example.demo.model.Message;

public interface MessageRepo extends JpaRepository<Message, Long> {
    List<Message> findByConversation(Conversation conversation);

    List<Message> findByConversationAndTimestampAfter(Conversation conversation, Instant timestamp);

    long countByConversation_UserAndRoleAndCreatedAfter(AppUser user, String role, Instant timestamp);
}
