package com.wipro.cloud.service;

import com.wipro.cloud.mongos.model.Notification;

import com.wipro.cloud.mongos.repository.NotificationRepo;
import com.wipro.cloud.relational.model.Users;
import com.wipro.cloud.relational.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
@Service
public class NotificationService {

    @Autowired
    NotificationRepo notificationRepo;
    @Autowired
    UserRepo userRepo;

    public List<Notification> getSubscription(String user){
      Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "time"));
      List<Notification> notificationList= notificationRepo.findByUserAndSort(user, sort);
      List<Notification> notifyList = new ArrayList<Notification>();
      notifyList.add(notificationList.get(0));
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
}
