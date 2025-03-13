package com.poseidoncapitalsolutions.trading.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.poseidoncapitalsolutions.trading.dto.UserDTO;
import com.poseidoncapitalsolutions.trading.exception.ResourceNotFoundException;
import com.poseidoncapitalsolutions.trading.mapper.UserMapper;
import com.poseidoncapitalsolutions.trading.model.User;
import com.poseidoncapitalsolutions.trading.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User user;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        // Initialize test data
        user = new User();
        user.setId(1);
        user.setUsername("testuser");
        user.setFullname("Test User");
        user.setPassword("password");
        user.setRole("USER");

        userDTO = new UserDTO();
        userDTO.setId(1);
        userDTO.setUsername("testuser");
        userDTO.setFullname("Test User");
        userDTO.setPassword("password");
        userDTO.setRole("USER");
    }

    @Test
    void findAllShouldReturnListOfUsers() {
        // Given
        List<User> users = Arrays.asList(user);
        when(userRepository.findAll()).thenReturn(users);

        // When
        List<User> result = userService.findAll();

        // Then
        assertEquals(1, result.size());
        assertEquals("testuser", result.get(0).getUsername());
    }

    @Test
    void findByIdShouldReturnUserWhenExists() {
        // Given
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        // When
        User result = userService.findById(1);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("testuser", result.getUsername());
    }

    @Test
    void findByIdShouldThrowExceptionWhenUserNotFound() {
        // Given
        when(userRepository.findById(anyInt())).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> userService.findById(99));
    }

    @Test
    void saveShouldEncodePasswordAndSaveUser() {
        // Given
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        // When
        User savedUser = userService.save(user);

        // Then
        verify(passwordEncoder, times(1)).encode("password");
        verify(userRepository, times(1)).save(user);
        assertEquals(user, savedUser);
    }

    @Test
    void updateShouldUpdateExistingUser() {
        // Given
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        // When
        userService.update(userDTO);

        // Then
        verify(userRepository, times(1)).findById(1);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void deleteShouldRemoveUser() {
        // When
        userService.delete(user);

        // Then
        verify(userRepository, times(1)).delete(user);
    }
}