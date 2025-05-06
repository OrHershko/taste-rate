package com.tasterate.service;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tasterate.model.User;
import com.tasterate.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User testUser;
    private final String TEST_EMAIL = "test@example.com";

    @BeforeEach
    void setUp() {
        // Create a test user
        testUser = new User();
        testUser.setId("user123");
        testUser.setDisplayName("Test User");
        testUser.setEmail(TEST_EMAIL);
        testUser.setProfileImageUrl("https://example.com/profile.jpg");
    }

    @Test
    @DisplayName("Should return user when email exists")
    void getUserByEmail_WhenEmailExists_ReturnsUser() {
        // Arrange
        when(userRepository.findByEmail(TEST_EMAIL)).thenReturn(Optional.of(testUser));

        // Act
        User result = userService.getUserByEmail(TEST_EMAIL);

        // Assert
        assertEquals(testUser.getId(), result.getId());
        assertEquals(testUser.getDisplayName(), result.getDisplayName());
        assertEquals(testUser.getEmail(), result.getEmail());
        assertEquals(testUser.getProfileImageUrl(), result.getProfileImageUrl());
    }

    @Test
    @DisplayName("Should throw exception when email doesn't exist")
    void getUserByEmail_WhenEmailDoesNotExist_ThrowsException() {
        // Arrange
        String nonExistentEmail = "nonexistent@example.com";
        when(userRepository.findByEmail(nonExistentEmail)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(
            RuntimeException.class,
            () -> userService.getUserByEmail(nonExistentEmail)
        );

        // Verify exception message
        assertEquals("User with email " + nonExistentEmail + " not found", exception.getMessage());
    }
}
