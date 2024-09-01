package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;

@Controller
@RequestMapping("/")
public class UserController {

    private final UserDetailsService userService;

    @Autowired
    public UserController(UserDetailsService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public String getUserProfile(Model model, Authentication authentication) {
        String userName = authentication.getName();
        User user = (User) userService.loadUserByUsername(userName);

        model.addAttribute("user", user);
        return "user";
    }

}