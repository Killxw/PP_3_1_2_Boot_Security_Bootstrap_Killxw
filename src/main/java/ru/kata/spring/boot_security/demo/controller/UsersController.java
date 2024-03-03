package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.authority.RoleType;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.Arrays;

@Controller
@RequestMapping("/users")
public class UsersController {
    private final UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/admin")
    public String printUsers(ModelMap model) {
        model.addAttribute("users", userService.listUsers());
        return "users";
    }
    @GetMapping("/user")
    public String showUser(Authentication authentication, Model model) {
        String username = authentication.getName();
        User user = userService.findUserByUsername(username).get();
        model.addAttribute("user", user);
        model.addAttribute("userRoles", user.getRoles());
        return "user";
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
    public String addUser(@ModelAttribute("user") User user) {
        userService.add(user);
        return "redirect:/users/admin";
    }

    @GetMapping("/delete")
    public String getDeleteForm(){
        return "delete";
    }

    @PostMapping("/del")
    public String delete(@RequestParam("id") Long userId) {
        userService.delete(userId);
        return "redirect:/users/admin";
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
        return "redirect:/users/admin";
    }

}