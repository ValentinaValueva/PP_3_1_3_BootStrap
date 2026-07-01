package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.HashSet;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

//    @GetMapping("/admin")
//    public String adminPage(Model model) {
//        model.addAttribute("users", userService.allUsers());
//        return "admin/index";
//    }

    @GetMapping
    public String getAllUsers(@AuthenticationPrincipal User currentUser, Model model) {
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("users", userService.allUsers());
        model.addAttribute("newUser", new User());
        model.addAttribute("roles", roleService.allRoles());
        return "admin/index";
    }

    @PostMapping
    public String createUser(@ModelAttribute("newUser") User user,
                             @RequestParam(value = "roleIds", required = false) List<Long> roleIds) {

        if (roleIds != null) {
            user.setRoles(new HashSet<>(roleService.findAllById(roleIds)));
        }

        userService.save(user);
        return "redirect:/admin";
    }

    @PatchMapping("/{userId}")
    public String updateUser(@ModelAttribute("user") User user,
                             @RequestParam(value = "roleIds", required = false) List<Long> roleIds,
                             @PathVariable Long userId) {

        if (roleIds != null) {
            user.setRoles(new HashSet<>(roleService.findAllById(roleIds)));
        }

        userService.update(user, userId);
        return "redirect:/admin";
    }

    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable Long userId) {
        userService.delete(userId);
        return "redirect:/admin";
    }

    @GetMapping("/{userId}")
    public String showUser(@PathVariable Long userId, Model model) {
        model.addAttribute("user", userService.show(userId));
        return "admin/show";
    }

    @GetMapping("/{userId}/edit")
    public String editUserForm(@PathVariable Long userId, Model model) {
        model.addAttribute("user", userService.show(userId));
        model.addAttribute("roles", roleService.allRoles());
        return "admin/edit";
    }

    @GetMapping("/new")
    public String newUserForm(@AuthenticationPrincipal User currentUser, Model model) {
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("newUser", new User());
        model.addAttribute("roles", roleService.allRoles());
        return "admin/new";
    }

}
