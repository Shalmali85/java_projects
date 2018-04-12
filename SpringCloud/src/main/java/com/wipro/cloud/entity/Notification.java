package com.wipro.cloud.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
public class Notification {
    @JsonProperty("endpoint")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String endpoint;
    @JsonProperty("keys")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private  Keys keys =new Keys();
    @JsonProperty("user")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String user;

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public Keys getKeys() {
        return keys;
    }

    public void setKeys(Keys keys) {
        this.keys = keys;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public  class Keys{
        private String p256dh;
        private String auth;

        public String getP256dh() {
            return p256dh;
        }

        public void setP256dh(String p256dh) {
            this.p256dh = p256dh;
        }

        public String getAuth() {
            return auth;
        }

        public void setAuth(String auth) {
            this.auth = auth;
        }
    }

}
