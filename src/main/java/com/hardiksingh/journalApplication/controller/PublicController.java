package com.hardiksingh.journalApplication.controller;

import com.hardiksingh.journalApplication.entity.User;
import com.hardiksingh.journalApplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @PostMapping("/create-user")
    public User saveUser(@RequestBody User user) {
        return userService.saveNewUser(user);
    }

}
