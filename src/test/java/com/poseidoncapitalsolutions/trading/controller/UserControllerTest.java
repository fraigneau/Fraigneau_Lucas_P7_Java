package com.poseidoncapitalsolutions.trading.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.poseidoncapitalsolutions.trading.dto.UserDTO;
import com.poseidoncapitalsolutions.trading.mapper.UserMapper;
import com.poseidoncapitalsolutions.trading.model.User;
import com.poseidoncapitalsolutions.trading.service.UserService;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private UserMapper userMapper;

    private User user;
    private UserDTO userDTO;
    private List<User> users;

    @BeforeEach
    void setUp() {
        // Initialize test data
        user = new User();
        user.setId(1);
        user.setUsername("testuser");
        user.setFullname("Test User");
        user.setPassword("Password1!");
        user.setRole("USER");

        userDTO = new UserDTO();
        userDTO.setId(1);
        userDTO.setUsername("testuser");
        userDTO.setFullname("Test User");
        userDTO.setPassword("Password1!");
        userDTO.setRole("USER");

        users = new ArrayList<>();
        users.add(user);
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void homeShouldReturnUserListPage() throws Exception {
        // Given
        when(userService.findAll()).thenReturn(users);

        // When & Then
        mockMvc.perform(get("/user/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/list"))
                .andExpect(model().attributeExists("users"))
                .andExpect(model().attribute("users", users));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void addUserShouldReturnAddUserPage() throws Exception {
        // When & Then
        mockMvc.perform(get("/user/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"))
                .andExpect(model().attributeExists("newUser"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void validateShouldRedirectToUserListWhenNoErrors() throws Exception {
        // Given
        when(userMapper.toEntity(any(UserDTO.class))).thenReturn(user);
        when(userService.save(user)).thenReturn(user);

        // When & Then
        mockMvc.perform(post("/user/validate")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("username", "testuser")
                .param("fullname", "Test User")
                .param("password", "Password1!")
                .param("role", "USER"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));

        verify(userService, times(1)).save(any(User.class));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void validateShouldReturnToAddPageWhenValidationErrors() throws Exception {
        // When & Then
        mockMvc.perform(post("/user/validate")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("username", "") // Empty username should cause validation error
                .param("fullname", "Test User")
                .param("password", "Password1!")
                .param("role", "USER"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void showUpdateFormShouldReturnUpdatePage() throws Exception {
        // Given
        when(userService.findById(anyInt())).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(userDTO);

        // When & Then
        mockMvc.perform(get("/user/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"))
                .andExpect(model().attributeExists("updatedUser"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void updateUserShouldRedirectToUserListWhenNoErrors() throws Exception {
        // Given
        doNothing().when(userService).update(any(UserDTO.class));

        // When & Then
        mockMvc.perform(post("/user/update/1")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "1")
                .param("username", "testuser")
                .param("fullname", "Test User")
                .param("password", "Password1!")
                .param("role", "USER"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));

        verify(userService, times(1)).update(any(UserDTO.class));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void deleteUserShouldRedirectToUserList() throws Exception {
        // Given
        when(userService.findById(anyInt())).thenReturn(user);
        doNothing().when(userService).delete(any(User.class));

        // When & Then
        mockMvc.perform(get("/user/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));

        verify(userService, times(1)).delete(any(User.class));
    }
}