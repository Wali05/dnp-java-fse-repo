package com.cognizant.springlearn.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
// REMOVE: import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtUserDetailsService.class);

    // Constructor inject PasswordEncoder (it's now a global bean)
    private final PasswordEncoder passwordEncoder;

    private final Map<String, UserDetails> inMemoryUsers = new HashMap<>();

    public JwtUserDetailsService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        LOGGER.info("Initializing in-memory users for JWT service...");
        inMemoryUsers.put("admin", User.builder()
            .username("admin")
            .password(passwordEncoder.encode("pwd"))
            .roles("ADMIN")
            .build());
        inMemoryUsers.put("user", User.builder()
            .username("user")
            .password(passwordEncoder.encode("pwd"))
            .roles("USER")
            .build());
        LOGGER.info("In-memory users initialized.");
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LOGGER.info("Attempting to load user: {}", username);
        UserDetails user = inMemoryUsers.get(username);
        if (user == null) {
            LOGGER.warn("User not found: {}", username);
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        LOGGER.info("User {} loaded successfully.", username);
        return user;
    }
}