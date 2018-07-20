package com.wipro.cloud.service;

import com.wipro.cloud.jsonEntity.Message;
import com.wipro.cloud.mongos.model.Notification;

import com.wipro.cloud.mongos.repository.NotificationRepo;
import com.wipro.cloud.relational.model.Users;
import com.wipro.cloud.relational.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Service
public class NotificationService {

    @Autowired
    NotificationRepo notificationRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    MessageClient client;


    public List<Notification> getSubscription(String user){
      Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "time"));
      List<Notification> notificationList= notificationRepo.findByUserAndSort(user, sort);
      List<Notification> notifyList = new ArrayList<Notification>();
        Notification notification = notificationList.get(0);
        System.out.println("message"+client.getNotificationMessage(user).getBody());
        Message message = new Message();
      notification.setMessage(client.getNotificationMessage(user).getBody().getMessage());
      notifyList.add(notification);
      return notifyList;
    }
    public List<Notification> getAllSubscription(){
        List<Notification> notificationList= notificationRepo.findAll();
        System.out.println("testing----");
        return notificationList;
    }

    public void saveSubscription(Notification notification){
        Notification notificationSubscriber= notificationRepo.findBySubscribedEndpoint(notification.getEndpoint());
        if(notificationSubscriber == null) {
            notificationRepo.save(notification);
        }
    }

    public void removeSubscription(Notification notification){
        Notification notificationSubscriber= notificationRepo.findBySubscribedEndpoint(notification.getEndpoint());
        if(notificationSubscriber != null) {
            notificationRepo.delete(notificationSubscriber.getEndpoint());
        }
    }
    @Transactional
    public void addUser(Users users){
        Users user = userRepo.save(users);

    }

    public List<Users> listUsers(){
        List<Users> userList = userRepo.findAll();
        return userList;


    }

    @FeignClient("SPRING-CLOUD-PCF-FEIGN-SERVER")
    interface MessageClient {
        @RequestMapping(value = "/message/{user}", method = GET)
        ResponseEntity<Message> getNotificationMessage(@PathVariable("user")String user );
    }
}
