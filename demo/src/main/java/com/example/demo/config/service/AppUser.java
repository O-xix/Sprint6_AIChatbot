package com.example.demo.config.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.example.demo.repo.AppUserRepo;
import com.example.demo.model.AppUser;

@Service
public class AppUser implements UserDetailsService {

    private final AppUserRepo users;

    public AppUser(AppUserRepo users) {
        this.users = users;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = appUserRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        return new User(appUser.getUsername(), appUser.getPassword(), appUser.getAuthorities());
    }
}

