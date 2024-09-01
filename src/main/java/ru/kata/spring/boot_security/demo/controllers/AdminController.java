package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.services.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.services.UserServiceImpl;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.ArrayList;

@Controller
@RequestMapping("/")
public class AdminController {
    private final UserServiceImpl userService;
    private final RoleServiceImpl roleService;

    @Autowired
    public AdminController(UserServiceImpl userService, RoleServiceImpl roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin")
    public String showAllUsers(@ModelAttribute("newUser") User user, Principal principal, Model model) {
        model.addAttribute("admin", userService.getUserByUsername(principal.getName()));
        model.addAttribute("allRoles", roleService.getAllRoles());
        model.addAttribute("allUsers", userService.getAllUsers());
        model.addAttribute("activeTable", "usersTable");
        return "admin";
    }


    @PostMapping("/admin")
    public String saveNewUser(@ModelAttribute("newUser") @Valid User user, BindingResult bindingResult,
                              Principal principal, Model model,
                              @RequestParam(name = "roles", required = true) List<Long> roleIds) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("admin", userService.getUserByUsername(principal.getName()));
            model.addAttribute("allRoles", roleService.getAllRoles());
            model.addAttribute("allUsers", userService.getAllUsers());
            model.addAttribute("activeTable", "addUser");
            return "admin";
        }
        List<Role> roles = new ArrayList<>();
        for (Long roleId : roleIds) {
            roles.add(roleService.getRoleById(roleId));
        }
        user.setRoles(roles);
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @PatchMapping("/admin")
    public String edit(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                       Model model, Principal principal) {

        model.addAttribute("admin", userService.getUserByUsername(principal.getName()));
        model.addAttribute("allRoles", roleService.getAllRoles());
        if (bindingResult.hasErrors()) {
            return "admin";
        }
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/admin")
    public String delete(@RequestParam("id") Long id) {
        userService.deleteUserById(id);
        return "redirect:/admin";
    }
}