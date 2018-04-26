package com.wipro.cloud.relational.model;


import javax.persistence.*;
import java.io.Serializable;



@Entity
@Table(name="users")
public class Users implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="userId")
    private Long userId;
    @Column(name = "userName")
    private String userName;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
