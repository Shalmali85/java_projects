package com.wipro.cloud.jsonEntity;

import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.annotation.JsonProperty;

public class Message {
    @JsonProperty("user")
    private String user;
    @JsonProperty("message")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
