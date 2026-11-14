package com.example.demo.service;
 
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 
import com.example.demo.model.AppUser;
import com.example.demo.repository.AppUserRepository;
import com.example.demo.repository.ConversationRepository;
 
@Service
public class UserService {
 
    private final AppUserRepository users;
    private final ConversationRepository conversations;
    private final PasswordEncoder passwordEncoder;
 
    public UserService(AppUserRepository users, ConversationRepository conversations,
                       PasswordEncoder passwordEncoder) {
        this.users = users;
        this.conversations = conversations;
        this.passwordEncoder = passwordEncoder;
    }
 
    // Throws IllegalArgumentException with a message the user can read if something is wrong
    public AppUser register(String username, String password) {
        username = username.trim().toLowerCase();
        if (users.existsByUsername(username)) {
            throw new IllegalArgumentException("An account with that username already exists.");
        }
        String problem = checkPassword(password);
        if (problem != null) {
            throw new IllegalArgumentException(problem);
        }
 
        AppUser user = new AppUser();
        user.setUsername(username);
        user.setPasswordHash(passwordEncoder.encode(password));
        return users.save(user);
    }
 
    // Returns what's wrong with the password, or null if it's fine
    public static String checkPassword(String password) {
        if (password == null || password.length() < 8) {
            return "Password must be at least 8 characters.";
        }
        if (!password.matches(".*[A-Za-z].*") || !password.matches(".*\\d.*")) {
            return "Password must contain at least one letter and one number.";
        }
        return null;
    }
 
    public AppUser getByUsername(String username) {
        return users.findByUsername(username).orElseThrow();
    }
 
    // Deletes all of the user's chats (and their messages), then the account
    @Transactional
    public void deleteAccount(AppUser user) {
        conversations.deleteByUser(user);
        users.delete(user);
    }
}