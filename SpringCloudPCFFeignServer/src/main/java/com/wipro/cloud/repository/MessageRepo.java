package com.wipro.cloud.repository;

import com.wipro.cloud.model.Message;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface MessageRepo extends MongoRepository<Message,String> {
    @Query("{ 'user' : ?0 }")
    Message findUserMessage(String user);
    @Query(value="{'user' : ?0}", delete = true)
    void delete(String user);

}
