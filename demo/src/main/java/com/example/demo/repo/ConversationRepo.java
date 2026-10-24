package com.example.demo.repo;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.AppUser;
import com.example.demo.model.Conversation;

public interface ConversationRepo extends JpaRepository<Conversation, Long> {
    Optional<Conversation> findByAppUser(AppUser appUser);

    Optional<Conversation> findByIdAndAppUser(Long id, AppUser appUser);

    void deleteByIdAndAppUser(Long id, AppUser appUser);
}
