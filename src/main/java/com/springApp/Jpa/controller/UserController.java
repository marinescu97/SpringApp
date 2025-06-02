package com.springApp.Jpa.controller;

import com.springApp.Jpa.dto.UserDto;
import com.springApp.Jpa.entity.Address;
import com.springApp.Jpa.entity.User;
import com.springApp.Jpa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/all")
    public ResponseEntity<Iterable<User>> createUsers(@RequestBody List<User> users){
        Iterable<User> createdUsers = service.saveAll(users);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUsers);
    }

    @GetMapping
    public ResponseEntity<Iterable<User>> getAllUsers(){
        Iterable<User> users = service.findAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        service.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> findByUsername(@PathVariable String username){
        Optional<User> user = service.findByUsername(username);

        if (user.isPresent()){
            return ResponseEntity.ok(user.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user){
        User saveUser = service.addUser(user);
        return ResponseEntity.ok(saveUser);
    }

    @PostMapping("/userDto")
    public ResponseEntity<User> createUserFromDto(@RequestBody UserDto userDto){
        User user = service.createUserFromDto(userDto);
        return ResponseEntity.ok(user);
    }
}
