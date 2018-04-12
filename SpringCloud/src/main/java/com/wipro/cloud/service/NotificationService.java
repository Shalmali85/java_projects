package com.wipro.cloud.service;

import com.wipro.cloud.model.Notification;
import com.wipro.cloud.repository.NotificationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class NotificationService {

    @Autowired
    NotificationRepo notificationRepo;

    public List<Notification> getSubscription(String user){
      Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "time"));
      List<Notification> notificationList= notificationRepo.findByUserAndSort(user, sort);
      List<Notification> notifyList = new ArrayList<Notification>();
      notifyList.add(notificationList.get(0));
      return notifyList;
    }
    public List<Notification> getAllSubscription(){
        List<Notification> notificationList= notificationRepo.findAll();
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
}
