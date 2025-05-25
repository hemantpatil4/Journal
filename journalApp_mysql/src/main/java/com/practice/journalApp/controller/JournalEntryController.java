package com.practice.journalApp.controller;

import com.practice.journalApp.entity.JournalEntry;
import com.practice.journalApp.service.JournalEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping
    public ResponseEntity<List<JournalEntry>> getAll() {
        return new ResponseEntity<>(journalEntryService.GetAllEntries(),HttpStatus.OK) ;
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<JournalEntry>> getAll(@PathVariable String username) {
        return new ResponseEntity<>(journalEntryService.GetAllEntriesForParticularUser(username),HttpStatus.OK) ;
    }

    @PostMapping("{username}")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry entry,@PathVariable String username) {
        try {
            journalEntryService.SaveEntry(entry,username);
            return new ResponseEntity<>(entry,HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/id/{ID}")
    public ResponseEntity<JournalEntry> getAllById(@PathVariable Long ID) {
        JournalEntry entry= journalEntryService.GetEntryById(ID).orElse(null);
        if(entry!=null){
            return new ResponseEntity<>(entry,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/id/{ID}/{username}")
    public void deleteJournalEntryById(@PathVariable Long ID,@PathVariable String username) {
        journalEntryService.DeleteById(ID,username);
    }

    @PutMapping("/id/{ID}")
    public JournalEntry updateJournalEntryById(@PathVariable Long ID, @RequestBody JournalEntry entry) {
        if (entry != null) {
            JournalEntry old = journalEntryService.GetEntryById(ID).orElse(null);
            if (old != null) {
                old.setContent(Objects.equals(entry.getContent(), "") ? old.getContent() : entry.getContent());
                old.setTitle(Objects.equals(entry.getTitle(), "") ? old.getTitle() : entry.getTitle());
                journalEntryService.SaveEntry(old);
                return  old;
            } else {
                journalEntryService.SaveEntry(entry);
                return entry;
            }
        }
        return null;
    }
}
