package com.practice.journalApp.repository;

import com.practice.journalApp.entity.JournalEntry;
import com.practice.journalApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    public User findByUserName(String userName);
}


