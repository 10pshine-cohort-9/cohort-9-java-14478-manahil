package com.example.cohort_9_java_14478_manahil.service;

import com.example.cohort_9_java_14478_manahil.dto.ChangePasswordRequest;
import com.example.cohort_9_java_14478_manahil.dto.UserDTO;
import com.example.cohort_9_java_14478_manahil.dto.UserResponseDTO;
import com.example.cohort_9_java_14478_manahil.entity.User;
import com.example.cohort_9_java_14478_manahil.exception.UserNotFoundException;
import com.example.cohort_9_java_14478_manahil.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private static final Logger logger =
            LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponseDTO createUser(UserDTO userDTO) {

        logger.info("Creating user with email: {}", userDTO.getEmail());

        User user = new User();

        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole(userDTO.getRole());

        User savedUser = userRepository.save(user);

        logger.info("User created successfully.");

        return mapToResponseDTO(savedUser);
    }

    public List<UserResponseDTO> getAllUsers() {

        logger.info("Fetching all users.");

        return userRepository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    public UserResponseDTO getUserById(Long id) {

        logger.info("Fetching user with ID: {}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("User not found with ID: {}", id);
                    return new UserNotFoundException(
                            "User not found with ID: " + id
                    );
                });

        return mapToResponseDTO(user);
    }

    public UserResponseDTO updateUser(Long id, UserDTO userDTO) {

        logger.info("Updating user with ID: {}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("User not found with ID: {}", id);
                    return new UserNotFoundException(
                            "User not found with ID: " + id
                    );
                });

        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPhoneNumber(userDTO.getPhoneNumber());

        if (userDTO.getPassword() != null &&
                !userDTO.getPassword().isEmpty()) {

            user.setPassword(
                    passwordEncoder.encode(userDTO.getPassword())
            );
        }

        user.setRole(userDTO.getRole());

        User updatedUser = userRepository.save(user);

        logger.info("User updated successfully.");

        return mapToResponseDTO(updatedUser);
    }

    public void deleteUser(Long id) {

        logger.info("Deleting user with ID: {}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("User not found with ID: {}", id);
                    return new UserNotFoundException(
                            "User not found with ID: " + id
                    );
                });

        userRepository.delete(user);

        logger.info("User deleted successfully.");
    }

    public void changePassword(String email,
                               ChangePasswordRequest request) {

        logger.info("Changing password for user: {}", email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    logger.warn("User not found with email: {}", email);
                    return new UserNotFoundException("User not found");
                });

        if (!passwordEncoder.matches(
                request.getCurrentPassword(),
                user.getPassword())) {

            throw new IllegalArgumentException(
                    "Current password is incorrect"
            );
        }

        user.setPassword(
                passwordEncoder.encode(request.getNewPassword())
        );

        userRepository.save(user);

        logger.info("Password changed successfully.");
    }

    private UserResponseDTO mapToResponseDTO(User user) {

        return new UserResponseDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getRole()
        );
    }
}