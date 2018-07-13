package com.wipro.cloud.repository;

import com.wipro.cloud.model.Users;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<Users,Integer> {


}
