package com.example.demo.controllers;

import com.example.demo.models.User;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", service.getAllUsers());
        return "users";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("user", new User());
        return "user-form";
    }

    @PostMapping
    public String save(@Valid @ModelAttribute User user, BindingResult binding) {
        if (binding.hasErrors()) {
            return "user-form";
        }
        service.saveUser(user);
        return "redirect:/users";
    }

    @GetMapping("/edit")
    public String editForm(@RequestParam Long id, Model model) {
        model.addAttribute("user", service.getUserById(id));
        return "user-form";
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute User user, BindingResult binding) {
        if (binding.hasErrors()) {
            return "user-form";
        }
        service.updateUser(user);
        return "redirect:/users";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam Long id) {
        service.deleteUser(id);
        return "redirect:/users";
    }
}
