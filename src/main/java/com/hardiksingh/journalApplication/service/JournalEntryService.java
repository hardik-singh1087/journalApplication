package com.hardiksingh.journalApplication.service;

import com.hardiksingh.journalApplication.entity.JournalEntry;
import com.hardiksingh.journalApplication.entity.User;
import com.hardiksingh.journalApplication.repository.JournalEntryRepository;
import com.hardiksingh.journalApplication.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//in the service we will write our business logic

@Service
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    public JournalEntry saveEntry(JournalEntry journalEntry, String userName) {
        User user = userService.findByUserName(userName);
        if (user == null) {
            throw new RuntimeException("User not found: " + userName);
        }
        JournalEntry saved = journalEntryRepository.save(journalEntry);
        user.getJournalEntries().add(saved);
        userService.saveUser(user);
        return saved;
    }

    public List<JournalEntry> getAllEntry() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> getEntryById(ObjectId id) {
        return journalEntryRepository.findById(id);
    }

    public void removeEntryById(ObjectId id, String userName) {
        User user = userService.findByUserName(userName);
        user.getJournalEntries().removeIf(entry -> entry.getId().equals(id));
        userService.saveUser(user);
        journalEntryRepository.deleteById(id);
    }
}