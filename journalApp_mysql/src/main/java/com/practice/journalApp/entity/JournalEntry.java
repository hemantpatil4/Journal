package com.practice.journalApp.entity;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;


import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "journal_entries")
@Data
public class JournalEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime date;
    // Optionally, you can add a relationship if it belongs to a user:

    @JsonBackReference
    @JsonIgnoreProperties({"journalEntries", "password"})
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
