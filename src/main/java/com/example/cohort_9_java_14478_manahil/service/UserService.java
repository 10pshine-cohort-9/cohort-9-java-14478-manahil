package com.example.cohort_9_java_14478_manahil.service;

import com.example.cohort_9_java_14478_manahil.dto.UserDTO;
import com.example.cohort_9_java_14478_manahil.entity.User;
import com.example.cohort_9_java_14478_manahil.exception.UserNotFoundException;
import com.example.cohort_9_java_14478_manahil.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public User createUser(UserDTO userDTO) {
        logger.info("Creating a new user with email: {}", userDTO.getEmail());
        User user = new User();

        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole(userDTO.getRole());
        User savedUser = userRepository.save(user);
        logger.info("User created successfully.");
        return savedUser;
    }

    public List<User> getAllUsers() {
        logger.info("Fetching all users.");
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        logger.info("Fetching user with ID: {}", id);

        return userRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("User not found with ID: {}", id);
                    return new UserNotFoundException("User not found with ID: " + id);
                });

    }

    public User updateUser(Long id, UserDTO userDTO) {
        logger.info("Updating user with ID: {}", id);
        User user = getUserById(id);

        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole(userDTO.getRole());
        User updatedUser = userRepository.save(user);
        logger.info("User updated successfully.");
        return updatedUser;
    }

    public void deleteUser(Long id) {
        logger.info("Deleting user with ID: {}", id);
        User user = getUserById(id);
        userRepository.delete(user);
        logger.info("User deleted successfully.");
    }
}
