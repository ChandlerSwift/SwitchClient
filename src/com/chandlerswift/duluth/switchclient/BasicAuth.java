/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chandlerswift.duluth.switchclient;

/**
 *
 * @author Chandler Swift
 */
public class BasicAuth {
    public BasicAuth(String username, String password) {
        user = username;
        pass = password;
    }
    
    public String getUser() {
        return user;
    }
    
    public String getPass() {
        return pass;
    }
    
    private String user;
    private String pass;
}
