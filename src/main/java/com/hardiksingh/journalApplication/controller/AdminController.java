package com.hardiksingh.journalApplication.controller;


import com.hardiksingh.journalApplication.entity.User;
import com.hardiksingh.journalApplication.service.JournalEntryService;
import com.hardiksingh.journalApplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }
    @GetMapping("/all-journals")
    public ResponseEntity<?> getAllJournals() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        return new ResponseEntity<>(journalEntryService.getAllEntry(), HttpStatus.OK);
    }

    @PostMapping("/create-admin")
    public User saveAdmin(@RequestBody User admin) {
        return userService.saveAdmin(admin);
    }

}
