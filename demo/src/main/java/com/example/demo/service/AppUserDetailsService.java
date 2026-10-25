package com.example.demo.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.sterotype.Service;

import com.example.demo.model.AppUser;
import com.example.demo.repo.AppUserRepo;

@Service
public class AppUserDetailsService implements UserDetailsService{
    
    private final AppUserRepo users;

    public AppUserDetailsService(AppUserRepo users) {
        this.users = users;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = users.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        
        return User.withUsername(user.getUsername())
            .password(user.getPasswordHash())
            .roles(user.getRole())
            .build();
    }
}
