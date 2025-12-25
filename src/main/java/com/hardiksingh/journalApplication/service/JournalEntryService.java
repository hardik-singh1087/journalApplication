package com.hardiksingh.journalApplication.service;

import com.hardiksingh.journalApplication.entity.JournalEntry;
import com.hardiksingh.journalApplication.entity.User;
import com.hardiksingh.journalApplication.repository.JournalEntryRepository;
import com.hardiksingh.journalApplication.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

//in the service we will write our business logic

@Service
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public JournalEntry saveEntry(JournalEntry journalEntry, String userName) {
        try {

            User user = userService.findByUserName(userName);

            JournalEntry saved = journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(saved);

            userService.saveUser(user);
            return saved;
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    public List<JournalEntry> getAllEntry() {
        return journalEntryRepository.findAll();
    }

    public JournalEntry getEntryById(ObjectId journalId, String userName) {
        User user = userService.findByUserName(userName);
        List<JournalEntry> journalEntries = user.getJournalEntries();
        for(JournalEntry journalEntry: journalEntries){
            if(Objects.equals(journalEntry.getId(), journalId)){
                return journalEntry;
            }
        }
        return null;
    }

    @Transactional
    public void removeEntryById(ObjectId id, String userName) {
        User user = userService.findByUserName(userName);
        user.getJournalEntries().removeIf(entry -> entry.getId().equals(id));
        userService.saveUser(user);
        journalEntryRepository.deleteById(id);
    }
}