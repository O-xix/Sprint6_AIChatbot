package com.example.demo.repo;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.AppUser;
import com.example.demo.model.Conversation;
import java.util.List;

public interface ConversationRepo extends JpaRepository<Conversation, Long> {
    List<Conversation> findByUserOrderByUpdatedAtDesc(AppUser user);

    Optional<Conversation> findByUserAndId(AppUser appUser, Long id);

    void deleteByUser(AppUser appUser);
}
