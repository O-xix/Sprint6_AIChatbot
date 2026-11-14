package com.example.demo.config.service;

import java.time.Duration;
import java.util.List;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.model.AppUser;
import com.example.demo.model.Conversation;
import com.example.demo.model.Message;
import com.example.demo.repo.ConversationRepo;
import com.example.demo.repo.MessageRepo;


public class ChatService {
    private final ConversationRepo conversations;
    private final MessageRepo messages;
    private final LlmService llm;
    private final int maxHistory;
    private final int maxMessageLength;
    private final int messagesPerHour;

    public ChatService(ConversationRepo conversations, MessageRepo messages, LlmService llm, int maxHistory, int maxMessageLength, int messagesPerHour) {
                        @Value("${chat.maxHistory}") int maxHistory,
                        @Value("${chat.maxMessageLength}") int maxMessageLength,
                        @Value("${chat.messagesPerHour}") int messagesPerHour) {
        this.conversations = conversations;
        this.messages = messages;
        this.llm = llm;
        this.maxHistory = maxHistory;
        this.maxMessageLength = maxMessageLength;
        this.messagesPerHour = messagesPerHour;

    }
    }

    public List<Conversation> getConversations(AppUser user) {
        return conversations.findByUserOrderByUpdatedAtDesc(user);
    }

    public Conversation get(Long id, AppUser user) {
        return conversations.findByIdAndUser(id, user)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Conversation not found"));
    }public Conversation create(AppUser user) {
        return conversations.save(new Conversation(user));
    }

    public Message send(Long conversationId, AppUser user, String text) {
        Conversation conversation = get(conversationId, user);

        text = text == null ? "" : text.trim();
        if (text.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Message cannot be empty");
        }
        if (text.length() > maxMessageLength) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Message exceeds maximum length of " + maxMessageLength);
        }

        messages.save(new Message(conversation, "user", text));

        if (conversation.getTitle().equals("New chat")) {
            conversation.setTitle(text.length() > 40 ? text.substring(0, 40) + "..." : text);
        }
        
        List<Message> recent = new ArrayList<>(
            messages.findByConversationOrderByCreatedAtDesc(conversation, PageRequest.of(0, maxHistory))
        );
        Collections.reverse(recent);
        List<LlmService.ChatMessages> history = recent.stream()
            .map(m -> new LlmService.ChatMessages(m.getRole(), m.getContent()))
            .toList();

        String replyText;
        try {
            replyText = llm.reply(history);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to get reply from LLM service", e);
        }

        Message reply = messages.save(new Message(conversation, "assistant", replyText));
        conversation.setUpdatedAt(Instant.now());
        conversations.save(conversation);
        return reply;
        
    }
}