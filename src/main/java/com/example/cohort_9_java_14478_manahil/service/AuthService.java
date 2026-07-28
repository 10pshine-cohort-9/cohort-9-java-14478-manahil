package com.example.cohort_9_java_14478_manahil.service;

import com.example.cohort_9_java_14478_manahil.dto.AuthResponse;
import com.example.cohort_9_java_14478_manahil.dto.LoginRequest;
import com.example.cohort_9_java_14478_manahil.entity.User;
import com.example.cohort_9_java_14478_manahil.repository.UserRepository;
import com.example.cohort_9_java_14478_manahil.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       JwtService jwtService) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    // Register
    public AuthResponse register(User user) {
        logger.info("Registering new user with email: {}", user.getEmail());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = userRepository.save(user);
        logger.info("User registered successfully.");
        UserDetails userDetails =
                org.springframework.security.core.userdetails.User
                        .withUsername(savedUser.getEmail())
                        .password(savedUser.getPassword())
                        .roles(savedUser.getRole() == null ? "USER" : savedUser.getRole())
                        .build();

        String token = jwtService.generateToken(userDetails);
        logger.info("JWT token generated successfully.");
        return new AuthResponse(token);
    }

    // Login
    public AuthResponse login(LoginRequest request) {
        logger.info("Login attempt for email: {}", request.getEmail());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        logger.info("User authenticated successfully.");
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> {
                    logger.warn("User not found: {}", request.getEmail());
                    return new RuntimeException("User not found");
                });

        UserDetails userDetails =
                org.springframework.security.core.userdetails.User
                        .withUsername(user.getEmail())
                        .password(user.getPassword())
                        .roles(user.getRole() == null ? "USER" : user.getRole())
                        .build();

        String token = jwtService.generateToken(userDetails);
        logger.info("JWT token generated successfully.");
        return new AuthResponse(token);
    }
}