package ru.kata.spring.boot_security.demo.configs;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.authority.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.Collections;

@Component
public class AdminUserInitializer implements CommandLineRunner {

    private final UserService userService;

    public AdminUserInitializer(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) {
        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword("admin");
        admin.setRoles(Collections.singletonList(new Role("ROLE_ADMIN")));

        if (userService.findUserByUsername("admin").isEmpty()) {
            userService.add(admin);
        }
    }
}