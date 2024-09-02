package ru.itsinfo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itsinfo.model.User;
import ru.itsinfo.service.AppService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    private final AppService appService;

    @Autowired
    public UserRestController(AppService appService) {
        this.appService = appService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return appService.findAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = appService.findUser(id);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        boolean saved = appService.saveUser(user, null, null);
        return saved ? ResponseEntity.status(HttpStatus.CREATED).body(user) : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        boolean updated = appService.saveUser(user, null, null);
        return updated ? ResponseEntity.ok(user) : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        appService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}