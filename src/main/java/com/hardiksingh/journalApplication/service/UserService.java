package com.hardiksingh.journalApplication.service;

import com.hardiksingh.journalApplication.entity.JournalEntry;
import com.hardiksingh.journalApplication.entity.User;
import com.hardiksingh.journalApplication.repository.JournalEntryRepository;
import com.hardiksingh.journalApplication.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //You must have two distinct methods: one that encodes (for new users)
    // and one that simply saves (for internal updates like adding journals).
    public User saveUser(User user) {
        //this requires no hashing
        return userRepository.save(user);
    }

    //this for creating new user
    //this hash the password
    public User saveNewUser(User user) {
        user.setPassword(requireNonNull(passwordEncoder.encode(user.getPassword())));
        user.setRoles(List.of("USER"));
        return userRepository.save(user);
    }

    public User saveAdmin(User admin) {
        admin.setPassword(requireNonNull(passwordEncoder.encode(admin.getPassword())));
        admin.setRoles(List.of("USER", "ADMIN"));
        return userRepository.save(admin);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> findById(ObjectId id) {
        return userRepository.findById(id);
    }

    @Transactional
    public User deleteUser(String userName, ObjectId userId) {
        User user = userRepository.findByUserName(userName);
        List<JournalEntry> journalEntries = user.getJournalEntries();

        if (Objects.equals(user.getUserId(), userId)) {
            for (JournalEntry journalEntry : journalEntries) {
                journalEntryRepository.deleteById(journalEntry.getId());
            }
            return userRepository.deleteByUserName(userName);
        }
        throw new RuntimeException("userName or userId is incorrect");
    }

    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }
}
