package com.anas.collaborative.collaborative_app.controller;

import com.anas.collaborative.collaborative_app.dto.LoginRequest;
import com.anas.collaborative.collaborative_app.dto.RegistrationRequest;
import com.anas.collaborative.collaborative_app.entity.User;
import com.anas.collaborative.collaborative_app.entity.UserRole;
import com.anas.collaborative.collaborative_app.repository.UserRepository;
import com.anas.collaborative.collaborative_app.service.RegistrationService;
import com.anas.collaborative.collaborative_app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final RegistrationService registrationService;
   /* private final AuthenticationManager authenticationManager; // For authenticating users
    private final UserDetailsService userDetailsService; // To load user details
*/
    @PostMapping("/user/{email}")
    public void changeToAdmin(@PathVariable String email) {
        userService.findByEmail(email).ifPresent(userEntity -> {
            userEntity.setRole(UserRole.ROLE_ADMIN);
            userService.save(userEntity);
        });
    }
    @GetMapping("/api/user/me")
    public Map<String, Object> getCurrentUser(Authentication authentication) {
        return Map.of("isAuthenticated", authentication != null && authentication.isAuthenticated());
    }
    @GetMapping("/profile")
    public ResponseEntity<User> getUserProfile(Principal principal) {
        String username = principal.getName();
        User user = userRepository.findByUsername(username).orElseThrow();
        return ResponseEntity.ok(user);
    }
   /* @PostMapping("/api/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            // Authenticate user
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            // If authentication is successful, return user details
            UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
            return ResponseEntity.ok(userDetails); // You can customize the response
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
*/
    // Logout endpoint (optional)
   /* @PostMapping("/api/auth/logout")
    public ResponseEntity<String> logout(Principal principal) {
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(request, response, authentication);
        return ResponseEntity.ok("Logged out successfully");
    }*/
    @GetMapping("/user")
    public ResponseEntity<Map<String, Object>> getUser(Authentication authentication) {
        Map<String, Object> response = new HashMap<>();
        response.put("authenticated", authentication != null && authentication.isAuthenticated());
        return ResponseEntity.ok(response);
    }
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegistrationRequest request) {
        try {
            User user = registrationService.registerUser(
                    request.getUsername(),
                    request.getEmail(),
                    request.getPassword()
            );
            return ResponseEntity.ok("User registered successfully with ID: " + user.getId());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
