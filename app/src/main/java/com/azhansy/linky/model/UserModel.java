package com.azhansy.linky.model;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

/**
 * Created by SHU on 2016/6/28.
 */
@AVClassName("UserModel")
public class UserModel extends AVObject{
    public String username;
    public String password;
    public String face;
    public String sessionToken;
    private String email;

    public UserModel() {
    }

    public UserModel(String name, String pass) {
        this.username = name;
        this.password = pass;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
