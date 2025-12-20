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

    @GetMapping
    public ResponseEntity<?> getAll() {
        if (journalEntryService.getAllEntry() == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(journalEntryService.getAllEntry(), HttpStatus.OK);
    }

    @GetMapping("/{userName}")
    public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String userName) {
        User user = userService.findByUserName(userName);
        if(user==null){
            return new ResponseEntity<>("User not found: " + userName, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user.getJournalEntries(), HttpStatus.OK);
    }

    @PostMapping("/{userName}")
    public ResponseEntity<?> createEntry(@RequestBody JournalEntry myEntry, @PathVariable String userName) {
        try {
            return new ResponseEntity<>(journalEntryService.saveEntry(myEntry, userName), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/id/{journalId}")
    public ResponseEntity<?> getJournalEntryById(@PathVariable ObjectId journalId) {

        Optional<JournalEntry> entryById = journalEntryService.getEntryById(journalId);
        if (entryById.isPresent()) {
            return new ResponseEntity<>(entryById.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        //getEntryById return --> Optional<JournalEntry>
        //Optional is just a case which can return or not return an object, so we have to write an orElse condition.
    }

    @DeleteMapping("/id/{userName}/{journalId}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId journalId, @PathVariable String userName) {
        Optional<JournalEntry> journalEntry = journalEntryService.getEntryById(journalId);
        journalEntryService.removeEntryById(journalId, userName);
        if (journalEntry.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("id/{userName}/{journalId}")
    public ResponseEntity<?> updateJournalEntryById(
            @PathVariable ObjectId journalId,
            @RequestBody JournalEntry newEntry,
            @PathVariable String userName
    ) {
        JournalEntry entry = journalEntryService.getEntryById(journalId).orElse(null);
        if (entry != null) {
            entry.setTitle(newEntry.getTitle());
            if (newEntry.getContent() != null) entry.setContent(newEntry.getContent());
            journalEntryService.saveEntry(entry, userName);
            return new ResponseEntity<>(entry, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
//controller will call the services, controller-->services