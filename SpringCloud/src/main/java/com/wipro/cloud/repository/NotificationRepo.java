package com.wipro.cloud.repository;

import com.wipro.cloud.model.Notification;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface NotificationRepo extends MongoRepository<Notification,String> {
   @Query("{ 'user' : ?0 }")
    List<Notification> findByUserAndSort(String user,Sort sort);
    @Query("{ 'endpoint' : ?0 }")
    Notification findBySubscribedEndpoint(String endpoint);
    @Query(value="{'endpoint' : ?0}", delete = true)
    void delete(String endpoint);

}
