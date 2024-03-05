package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.authority.RoleType;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.Arrays;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping
    public String printUsers(ModelMap model) {
        model.addAttribute("users", userService.listUsers());
        return "users";
    }
    @GetMapping("/logoutSuccess")
    public String logout(){
        return "logoutSuccess";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", Arrays.asList(RoleType.values()));
        return "add";
    }

    @PostMapping("/add")
    public String addUser(@ModelAttribute("user") User user, Model model) {
        try {
            userService.add(user);
            return "redirect:/admin";
        }  catch (DataIntegrityViolationException e) {
            model.addAttribute("error", "Username is already taken");
            model.addAttribute("roles", Arrays.asList(RoleType.values()));
            return "add";
        }
    }

    @GetMapping("/delete")
    public String getDeleteForm(){
        return "delete";
    }

    @PostMapping("/del")
    public String delete(@RequestParam("id") Long userId) {
        userService.delete(userId);
        return "redirect:/admin";
    }

    @GetMapping("/change")
    public String showEditForm(@RequestParam("username") String username, Model model) {
        User user;
        if (userService.findUserByUsername(username).isPresent()) {
            user = userService.findUserByUsername(username).get();
        } else {
            return "cantFind";
        }
        model.addAttribute("roles", Arrays.asList(RoleType.values()));
        model.addAttribute("user", user);
        return "change";
    }

    @PostMapping("/change")
    public String changeUser(@ModelAttribute("user") User user) {
        userService.change(user);
        return "redirect:/admin";
    }
}
