package com.anas.collaborative.collaborative_app.service;

import com.anas.collaborative.collaborative_app.dto.RegistrationRequest;
import com.anas.collaborative.collaborative_app.entity.Role;
import com.anas.collaborative.collaborative_app.entity.User;
import com.anas.collaborative.collaborative_app.repository.RoleRepository;
import com.anas.collaborative.collaborative_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class RegistrationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(String username, String email, String password) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email is already registered!");
        }

        // Create a new user and assign the default role (e.g., ROLE_USER)
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setEnabled(true); // Account enabled by default

        Role defaultRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Default role not found"));

        user.setRoles(Set.of(defaultRole));
        return userRepository.save(user);
    }
}