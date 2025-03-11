package com.poseidoncapitalsolutions.trading.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.poseidoncapitalsolutions.trading.dto.UserDTO;
import com.poseidoncapitalsolutions.trading.mapper.UserMapper;
import com.poseidoncapitalsolutions.trading.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Controller
@Tag(name = "User Management", description = "API for managing users in the system")
public class UserController {

    private UserService userService;
    private UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @Operation(summary = "Get all users", description = "Returns a list of all users in the system")
    @GetMapping("/user/list")
    public String home(Model model) {
        model.addAttribute("users", userService.findAll());
        return "user/list";
    }

    @Operation(summary = "Show add user form", description = "Displays form for adding a new user")
    @GetMapping("/user/add")
    public String addUser(Model model) {
        model.addAttribute("newUser", new UserDTO());
        return "user/add";
    }

    @Operation(summary = "Create new user", description = "Creates a new user with the provided details")
    @PostMapping("/user/validate")
    public String validate(
            @Parameter(description = "User data to be created", required = true) @ModelAttribute("newUser") @Valid UserDTO user,
            BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("newUser", user);
            return "user/add";
        }

        userService.save(userMapper.toEntity(user));
        return "redirect:/user/list";
    }

    @Operation(summary = "Show update user form", description = "Displays form for updating an existing user")
    @GetMapping("/user/update/{id}")
    public String showUpdateForm(
            @Parameter(description = "ID of the user to be updated", required = true) @PathVariable("id") int id,
            Model model) {
        model.addAttribute("updatedUser", userMapper.toDto(userService.findById(id)));
        return "user/update";
    }

    @Operation(summary = "Update existing user", description = "Updates an existing user with the provided details")
    @PostMapping("/user/update/{id}")
    public String updateUser(
            @Parameter(description = "ID of the user to be updated", required = true) @PathVariable("id") int id,
            @Parameter(description = "Updated user data", required = true) @ModelAttribute("updatedUser") @Valid UserDTO user,
            BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("updatedUser", user);
            return "user/update";
        }

        userService.update(user);
        return "redirect:/user/list";
    }

    @Operation(summary = "Delete user", description = "Deletes a user from the system")
    @GetMapping("/user/delete/{id}")
    public String deleteUser(
            @Parameter(description = "ID of the user to be deleted", required = true) @PathVariable("id") int id,
            Model model) {
        userService.delete(userService.findById(id));
        return "redirect:/user/list";
    }
}