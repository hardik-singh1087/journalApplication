package com.hardiksingh.journalApplication.controller;

import com.hardiksingh.journalApplication.entity.JournalEntry;
import com.hardiksingh.journalApplication.entity.User;
import com.hardiksingh.journalApplication.service.JournalEntryService;
import com.hardiksingh.journalApplication.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@RestController      //component with special powers
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping()
    public ResponseEntity<?> getAllJournalEntriesOfUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        User user = userService.findByUserName(userName);

        if (user.getJournalEntries() == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user.getJournalEntries(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> createEntry(@RequestBody JournalEntry myEntry) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            return new ResponseEntity<>(journalEntryService.saveEntry(myEntry, userName), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/id/{journalId}")
    public ResponseEntity<?> getJournalEntryById(@PathVariable ObjectId journalId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        JournalEntry journalEntry = journalEntryService.getEntryById(journalId, userName);
        if (journalEntry == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(journalEntry, HttpStatus.OK);
    }

    @DeleteMapping("/id/{journalId}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId journalId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        JournalEntry journalEntry = journalEntryService.getEntryById(journalId, userName);
        if (journalEntry == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        try {
            journalEntryService.removeEntryById(journalId, userName);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("id/{journalId}")
    public ResponseEntity<?> updateJournalEntryById(
            @PathVariable ObjectId journalId,
            @RequestBody JournalEntry newEntry
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        JournalEntry journalEntry = journalEntryService.getEntryById(journalId, userName);
        if (journalEntry == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        try {
            journalEntry.setTitle(newEntry.getTitle());
            if (newEntry.getContent() != null) journalEntry.setContent(newEntry.getContent());
            journalEntryService.saveEntry(journalEntry,userName);
            return new ResponseEntity<>(journalEntry, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
//controller will call the services, controller-->services