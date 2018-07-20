package com.wipro.cloud.service;

import com.wipro.cloud.model.Message;

import com.wipro.cloud.repository.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Service

public class MessageService {

    @Autowired
    MessageRepo messageRepo;


    public Message getMessage(String user){
      Message message = messageRepo.findUserMessage(user);
      return message;
    }
    public List<Message> getAllMessages(){
        List<Message> messageList = messageRepo.findAll();
        System.out.println("testing message----");
        return messageList;
    }

    public void saveMessage(Message message){
        Message messageSubscriber = messageRepo.findUserMessage(message.getUser());
        if(messageSubscriber == null) {
            messageRepo.save(message);
        }
    }

    public void removeMessage(Message message){
        Message messageSubscriber = messageRepo.findUserMessage(message.getUser());
        if(messageSubscriber != null) {
            messageRepo.delete(messageSubscriber.getUser());
        }
    }



}
