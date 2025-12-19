package com.hardiksingh.journalApplication.service;

import com.hardiksingh.journalApplication.entity.JournalEntry;
import com.hardiksingh.journalApplication.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//in the service we will write our business logic

@Service
public class JournalEntryService {
    @Autowired
    private JournalEntryRepository journalEntryRepository;

    public JournalEntry saveEntry(JournalEntry journalEntry) {
        return journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAllEntry() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> getEntryById(ObjectId id) {
        return journalEntryRepository.findById(id);
    }

    public void removeEntryById(ObjectId id) {
        journalEntryRepository.deleteById(id);
    }
}