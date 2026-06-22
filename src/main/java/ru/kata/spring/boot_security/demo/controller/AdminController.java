package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.allUsers());
        return "admin/index";
    }

    @GetMapping("/{userId}")
    public String getUser(@PathVariable Long userId, Model model) {
        model.addAttribute("user", userService.show(userId));
        return "admin/show";
    }

    @GetMapping("/new")
    public String createUserForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.allRoles());
        return "admin/new";
    }

    @PostMapping
    public String createUser(@ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:/admin";
    }

    @GetMapping("/{userId}/edit")
    public String updateUserForm(@PathVariable Long userId, Model model) {
        model.addAttribute("user", userService.show(userId));
        model.addAttribute("roles", roleService.allRoles());
        return "admin/edit";
    }

    @PatchMapping("/{userId}")
    public String updateUser(@ModelAttribute("user") User user,
                             @PathVariable Long userId) {
        userService.update(user, userId);
        return "redirect:/admin";
    }

    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable Long userId) {
        userService.delete(userId);
        return "redirect:/admin";
    }
}