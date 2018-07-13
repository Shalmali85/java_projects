package com.wipro.cloud.controller;

import com.wipro.cloud.model.Users;
import com.wipro.cloud.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@EnableAutoConfiguration

public class NotificationController {
    @Autowired
    NotificationService notificationService;
    @RequestMapping(value="/users",
            method=RequestMethod.POST,
            produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addUsers(@RequestBody Users users){
        notificationService.addUser(users);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value="/users",
            method=RequestMethod.GET,
            produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Users>> getUsers(){
        List<Users> userList =  notificationService.listUsers();
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @RequestMapping(value="/test",
            method=RequestMethod.GET,
            produces=MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> test(){

        return new ResponseEntity<>("testing springaws", HttpStatus.OK);
    }
}
