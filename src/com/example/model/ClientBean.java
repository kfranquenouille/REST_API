package com.example.model;

/**
 * Created by kevin on 20/02/15.
 */
public class ClientBean {

    private String password;
    private String username;

    public ClientBean(){
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
