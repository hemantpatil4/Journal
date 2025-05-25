package com.practice.journalApp.controller;

import com.practice.journalApp.entity.JournalEntry;
import com.practice.journalApp.entity.LoginRequestDTO;
import com.practice.journalApp.entity.User;
import com.practice.journalApp.service.JournalEntryService;
import com.practice.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.GetAllUsers(),HttpStatus.OK) ;
    }

    @PostMapping
    public void CreateUser( @RequestBody User user){
        userService.SaveEntry(user);
    }

    @PutMapping("/{userName}")
    public ResponseEntity<?> UpdateUser(@RequestBody User user,@PathVariable String userName){
        User userindb=userService.findByUserName(userName);
        if(userindb!=null){
            userindb.setUserName(user.getUserName());
            userindb.setPassword(user.getPassword());
            userService.SaveEntry(userindb);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/find")
    public ResponseEntity<?> findUser(@RequestBody LoginRequestDTO loginRequest) {
        User user = userService.findByUserName(loginRequest.getUserName());
        if (user != null && userService.checkPassword(loginRequest.getPassword(), user.getPassword())) {
            return ResponseEntity.ok(user); // Or map to DTO to avoid returning password
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }
}
