package com.wipro.cloud.relational.repository;

import com.wipro.cloud.relational.model.Users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface UserRepo extends JpaRepository<Users,Integer> {


}
