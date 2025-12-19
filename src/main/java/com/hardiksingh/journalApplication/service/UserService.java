package com.hardiksingh.journalApplication.service;

import com.hardiksingh.journalApplication.entity.User;
import com.hardiksingh.journalApplication.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user){
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> findById(ObjectId id){
        return userRepository.findById(id);
    }

    public void removeEntryById(ObjectId id){
        userRepository.deleteById(id);
    }

    public User findByUserName(@RequestBody String userName){
        return userRepository.findByUserName(userName);
    }
}
