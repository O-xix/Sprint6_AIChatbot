package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.service.UserService;

@Controller
public class AuthController {
    // Data fields
    private final UserService userService;

    // Constructor
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // To go to the login page
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // To go to the register page
    @GetMapping("/register")
    public String registerForm() {
        return "register";
    }

    // Registering a new user
    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password, @RequestParam String confirmPassword, Model model) {
        try {
            // Check if passwords match
            if (!password.equals(confirmPassword)) {
                throw new IllegalArgumentException("The passwords don't match.");

            }
            // Registering a new user
            userService.register(username, password);
            // Redirect to login page with a success message
            return "redirect:/login?registered";
        }catch (IllegalArgumentException e) {
            // Add error message to the model
            model.addAttribute("error", e.getMessage());
            // Add username to the model
            model.addAttribute("username", username);
            // Return to the register page
            return "register";
        }
    }
}
