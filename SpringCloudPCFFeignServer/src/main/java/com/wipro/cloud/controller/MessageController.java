package com.wipro.cloud.controller;

import com.wipro.cloud.model.Message;
import com.wipro.cloud.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
public class MessageController {
    @Autowired
    MessageService messageService;


    @RequestMapping(value="/message/{user}",
                   method=RequestMethod.GET,
                   produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Message> getUserNotificationDetails(@PathVariable("user")String user){
        Message messageEntity =  messageService.getMessage(user);
        return new ResponseEntity<>(messageEntity, HttpStatus.OK);
    }
    @RequestMapping(value="/message/all",
            method=RequestMethod.GET,
            produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Message>> getAllNotificationDetails(){
        List<Message> messageEntityList =  messageService.getAllMessages();
        return new ResponseEntity<>(messageEntityList, HttpStatus.OK);
    }

    @RequestMapping(value="/message",
            method=RequestMethod.POST)
    public ResponseEntity subscribeUser(@RequestBody Message message){
        messageService.saveMessage(message);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @RequestMapping(value="/message",
            method=RequestMethod.DELETE,
            produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity removeSubscription(@RequestBody Message message){
        messageService.removeMessage(message);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
