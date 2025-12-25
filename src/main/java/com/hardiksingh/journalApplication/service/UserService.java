package com.hardiksingh.journalApplication.service;

import com.hardiksingh.journalApplication.entity.User;
import com.hardiksingh.journalApplication.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

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

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> findById(ObjectId id) {
        return userRepository.findById(id);
    }

    public void removeEntryById(ObjectId id) {
        userRepository.deleteById(id);
    }
    public User deleteByUserName(String userName) {
        return userRepository.deleteByUserName(userName);
    }

    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }
}
