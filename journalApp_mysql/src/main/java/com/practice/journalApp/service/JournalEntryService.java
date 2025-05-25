package com.practice.journalApp.service;

import com.practice.journalApp.entity.JournalEntry;
import com.practice.journalApp.entity.User;
import com.practice.journalApp.repository.JournalEntryRepository;
import com.practice.journalApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {
    @Autowired
    private JournalEntryRepository repository;

    @Autowired
    private UserRepository userRepository;

    public void SaveEntry(JournalEntry entry, String username){
        User user= userRepository.findByUserName(username);
        if (user != null) {
            entry.setDate(LocalDateTime.now());
            entry.setUser(user); // ✅ Set the owning side of the relationship

            repository.save(entry); // ✅ Now user_id will be set correctly

            user.getJournalEntries().add(entry); // optional: if you want to maintain both sides
            userRepository.save(user); // optional: only needed if you're relying on cascading
        }

    }

    public void SaveEntry(JournalEntry entry){
            entry.setDate(LocalDateTime.now());
            repository.save(entry);

    }

    public List<JournalEntry> GetAllEntries(){
        return repository.findAll();
    }

    public Optional<JournalEntry> GetEntryById(Long id){
        return repository.findById(id);
    }

    public void DeleteById(Long id, String username){
        User user= userRepository.findByUserName(username);
        user.getJournalEntries().removeIf(x->x.getId().equals(id));
        userRepository.save(user);
         repository.deleteById(id);
    }

    public List<JournalEntry> GetAllEntriesForParticularUser(String username) {
       User user= userRepository.findByUserName(username);
        return user.getJournalEntries();

    }
}
