package com.poseidoncapitalsolutions.trading.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poseidoncapitalsolutions.trading.dto.UserDTO;
import com.poseidoncapitalsolutions.trading.mapper.UserMapper;
import com.poseidoncapitalsolutions.trading.service.UserService;

import jakarta.validation.Valid;

@Controller
public class UserController {

    private UserService userService;
    private UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @RequestMapping("/user/list")
    public String home(Model model) {
        model.addAttribute("users", userService.findAll());
        return "user/list";
    }

    @GetMapping("/user/add")
    public String addUser(Model model) {
        model.addAttribute("newUser", new UserDTO());
        return "user/add";
    }

    @PostMapping("/user/validate")
    public String validate(@ModelAttribute("newUser") @Valid UserDTO user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("newUser", user);
            return "user/add";
        }

        userService.save(userMapper.toEntity(user));
        return "redirect:/user/list";
    }

    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") int id, Model model) {
        model.addAttribute("updatedUser", userMapper.toDto(userService.findById(id)));
        return "user/update";
    }

    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") int id,
            @ModelAttribute("updatedUser") @Valid UserDTO user,
            BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("updatedUser", user);
            return "user/update";
        }

        userService.update(user);
        return "redirect:/user/list";
    }

    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") int id, Model model) {
        userService.delete(userService.findById(id));
        return "redirect:/user/list";
    }
}
