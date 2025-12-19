package com.hardiksingh.journalApplication.controller;

import com.hardiksingh.journalApplication.entity.JournalEntry;
import com.hardiksingh.journalApplication.service.JournalEntryService;
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


    @GetMapping
    public ResponseEntity<?> getAll() {
        if (journalEntryService.getAllEntry() == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(journalEntryService.getAllEntry(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createEntry(@RequestBody JournalEntry myEntry) {
        try {
            ;
            return new ResponseEntity<>(journalEntryService.saveEntry(myEntry), HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/id/{myId}")
    public ResponseEntity<?> getJournalEntryById(@PathVariable ObjectId myId) {

        Optional<JournalEntry> entryById = journalEntryService.getEntryById(myId);
        if (entryById.isPresent()) {
            return new ResponseEntity<>(entryById.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        //getEntryById return --> Optional<JournalEntry>
        //Optional is just a case which can return or not return an object, so we have to write an orElse condition.
    }

    @DeleteMapping("/id/{myId}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myId) {
        Optional<JournalEntry> entryById = journalEntryService.getEntryById(myId);
        journalEntryService.removeEntryById(myId);
        if (entryById.isPresent()) {
            return new ResponseEntity<>(entryById.get(), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("id/{myId}")
    public ResponseEntity<?> updateJournalEntryById(@PathVariable ObjectId myId, @RequestBody JournalEntry newEntry) {
        Optional<JournalEntry> entryById = journalEntryService.getEntryById(myId);
        if (entryById.isPresent()) {
            if (newEntry.getTitle() != null) entryById.get().setTitle(newEntry.getTitle());
            if (newEntry.getContent() != null) entryById.get().setContent(newEntry.getContent());
            journalEntryService.saveEntry(entryById.get());
            return new ResponseEntity<>(entryById.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
//controller will call the services, controller-->services