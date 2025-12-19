package com.hardiksingh.journalApplication.controller;

import com.hardiksingh.journalApplication.entity.User;
import com.hardiksingh.journalApplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();

    }

    @GetMapping("/{userName}")
    public ResponseEntity<?> findByUserName(@PathVariable String userName) {
        User user = userService.findByUserName(userName);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public User saveUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @PutMapping("/{userName}")
    public User updateUser(@RequestBody User newUser, @PathVariable String userName) {
        User userInDB = userService.findByUserName(userName);
        if (userInDB != null) {
            userInDB.setUserName(newUser.getUserName());
            userInDB.setPassword(newUser.getPassword());
        }
        return userService.saveUser(userInDB);
    }
}
