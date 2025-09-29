package com.example.todolist.controller;

import com.example.todolist.model.User;
import com.example.todolist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register/save")
    public String registration(@ModelAttribute("user") User user, Model model) {
        User existingUser = userService.findByUsername(user.getUsername());
        if (existingUser != null) {
            model.addAttribute("errorMessage", "There is already an account registered with that username");
            return "register";
        }
        userService.save(user);
        return "redirect:/login?register_success";
    }
}