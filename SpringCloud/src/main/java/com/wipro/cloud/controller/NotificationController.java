package com.wipro.cloud.controller;

import com.sun.media.jfxmedia.Media;
import com.wipro.cloud.model.Notification;
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

    @RequestMapping(value="/notify/{user}",
                   method=RequestMethod.GET,
                   produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Notification>> getUserNotificationDetails(@PathVariable("user")String user){
        List<Notification> notificationEntityList =  notificationService.getSubscription(user);
        return new ResponseEntity<>(notificationEntityList, HttpStatus.OK);
    }
    @RequestMapping(value="/notify/all",
            method=RequestMethod.GET,
            produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Notification>> getAllNotificationDetails(){
        List<Notification> notificationEntityList =  notificationService.getAllSubscription();
        return new ResponseEntity<>(notificationEntityList, HttpStatus.OK);
    }

    @RequestMapping(value="/notify",
            method=RequestMethod.POST)
    public ResponseEntity subscribeUser(@RequestBody Notification notification){
        notificationService.saveSubscription(notification);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @RequestMapping(value="/notify",
            method=RequestMethod.DELETE,
            produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity removeSubscription(@RequestBody Notification notification){
        notificationService.removeSubscription(notification);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
