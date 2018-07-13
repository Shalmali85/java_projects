package com.wipro.cloud.service;


import com.wipro.cloud.model.Users;
import com.wipro.cloud.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
@Service
public class NotificationService {


    @Autowired
    UserRepo userRepo;


    @Transactional
    public void addUser(Users users){
        Users user = userRepo.save(users);

    }

    public List<Users> listUsers(){
        List<Users> userList = userRepo.findAll();
        return userList;


    }
}
