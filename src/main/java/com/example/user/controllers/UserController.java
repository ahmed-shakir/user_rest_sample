package com.example.user.controllers;

import com.example.user.entities.User;
import com.example.user.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST-API endpoints for user
 *
 * @author Ahmed Shakir
 * @version 1.0
 * @since 2020-10-15
 */
// @Controller // MVC med statiska html sidor
@RestController // REST API Endpoints
@RequestMapping("/api/v1/users")
@Slf4j // logging
public class UserController {
    //final Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;
    //private List<User> users;

    @Secured({"ROLE_ADMIN", "ROLE_EDITOR", "ROLE_USER"})
    @GetMapping
    public ResponseEntity<List<User>> findAllUsers(@RequestParam(required = false) String name, @RequestParam(required = false) boolean sort) {
        // var users = userService.findAll(); // List<User> users = userService.findAll();
        // this.users = users;
        // return new ResponseEntity<>(users, HttpStatus.OK);
        // return ResponseEntity.ok(users);
        return ResponseEntity.ok(userService.findAll(name, sort));
    }

    @Secured({"ROLE_ADMIN", "ROLE_EDITOR"})
    @GetMapping("/{id}") // /api/v1/users/{id} -> localhost:7000/api/v1/users/{id}
    public ResponseEntity<User> findUserById(@PathVariable String id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @Secured("ROLE_ADMIN")
    @PostMapping
    public ResponseEntity<User> saveUser(@Validated @RequestBody User user) {
        return ResponseEntity.ok(userService.save(user));
    }

    /*@PutMapping("/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable String id, @RequestBody User user) {
        userService.update(id, user);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }*/

    @Secured({"ROLE_ADMIN", "ROLE_EDITOR", "ROLE_USER"})
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUser(@PathVariable String id, @Validated @RequestBody User user) {
        userService.update(id, user);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable String id) {
        userService.delete(id);
    }
}
