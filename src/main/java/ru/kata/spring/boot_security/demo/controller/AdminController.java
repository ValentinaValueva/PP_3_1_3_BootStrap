package ru.kata.spring.boot_security.demo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.service.RoleService;

import javax.validation.Valid;
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

    @GetMapping
    public String index(Model model) {
        model.addAttribute("users", userService.allUsers());
        return "admin/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.show(id));
        return "admin/show";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("roles", roleService.allRoles());
        return "admin/new";
    }

    @PostMapping
    public String create(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                         @RequestParam(value = "roleIds", required = false) List<Long> roleIds, Model model) {
        if (bindingResult.hasErrors() || user.getPassword() == null || user.getPassword().isBlank()) {
            model.addAttribute("roles", roleService.allRoles());
            if (user.getPassword() == null || user.getPassword().isBlank()) {
                model.addAttribute("passwordError", "Password should not be empty");
            }
            return "admin/new";
        }
        if (roleIds != null) {
            user.setRoles(new HashSet<>(roleService.findAllById(roleIds)));
        }
        userService.save(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.show(id));
        model.addAttribute("roles", roleService.allRoles());
        return "admin/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                         @RequestParam(value = "roleIds", required = false) List<Long> roleIds,
                         @PathVariable Long id, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", roleService.allRoles());
            return "admin/edit";
        }
        if (roleIds != null) {
            user.setRoles(new HashSet<>(roleService.findAllById(roleIds)));
        }
        userService.update(user, id);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }
}
