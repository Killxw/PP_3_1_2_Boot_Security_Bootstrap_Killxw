package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.authority.RoleType;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class RestAdminController {
    private final UserService userService;

    @Autowired
    public RestAdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.listUsers(), HttpStatus.OK);
    }
    @GetMapping("/currentUser")
    public ResponseEntity<User> getCurrentUser(Authentication authentication){
        String username = authentication.getName();
        User user = userService.findUserByUsername(username).get();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addUser(@RequestBody User user) {
        try {
            userService.add(user);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/delete")
    public ResponseEntity<HttpStatus> deleteUser(@RequestParam long id) {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/findUser")
    public ResponseEntity<User> findUser(@RequestParam("username") String username){
        return new ResponseEntity<>(userService.findUserByUsername(username).get(), HttpStatus.OK);
    }

    @PostMapping("/change")
    public ResponseEntity<HttpStatus> updateUser(@RequestBody User user) {
        userService.change(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/roles")
    public ResponseEntity<List<RoleType>> getRoles(){
        return new ResponseEntity<>(Arrays.asList(RoleType.values()), HttpStatus.OK);
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Username is already taken")
    static
    class UsernameAlreadyTakenException extends RuntimeException {
    }
}