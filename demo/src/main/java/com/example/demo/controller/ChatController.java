package com.example.demo.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.config.service.ChatService;
import com.example.demo.model.AppUser;
import com.example.demo.model.Conversation;
import com.example.demo.config.service.UserService;


public class ChatController {
    private final ChatService chatService;
    private final UserService userService;

    public ChatController(ChatService chatService, UserService userService) {
        this.chatService = chatService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/chat";
    }

    @GetMapping ("/chat") {
        public String latestChat(Principal principal) {
            AppUser user = userService.findByUsername(principal.getName());
            List<Conversation> chats = chatService.list(user);
            Conversation chat = chats.isEmpty() ? chatService.create(user) : chats.get(0);
            return "redirect:/chat/" + chat.getId();

        }
    }

    @GetMapping("/chat/{id}")
    public String chat(@PathVariable Long id, Principal principal, Model model) {
        AppUser user = userService.findByUsername(principal.getName());
        model.addAttribute("conversations", chatService.list(user));
        model.addAttribute("current", chatService.get(id, user));
        return "chat";
    }

    @PostMapping("/chat/new")
    public String newChat(Principal principal) {
        AppUser user = userService.findByUsername(principal.getName());
        return "redirect:/chat/" + chatService.create(user).getId();
    }
}
