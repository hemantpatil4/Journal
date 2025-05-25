package com.practice.journalApp.service;

import com.practice.journalApp.entity.User;
import com.practice.journalApp.entity.User;
import com.practice.journalApp.repository.UserRepository;
import com.practice.journalApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void SaveEntry(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public List<User> GetAllUsers(){
        return userRepository.findAll();
    }

    public Optional<User> GetEntryById(Long id){
        return userRepository.findById(id);
    }

    public void DeleteById(Long id){
        userRepository.deleteById(id);
    }

    public User findByUserName(String userName){
        return userRepository.findByUserName(userName);

    }
    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
