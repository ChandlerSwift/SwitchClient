/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chandlerswift.duluth.switchclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Chandler Swift
 */
public class Light {
    public Light(int new_id) {
        SwitchClient.log("Constructor Called");
        id = new_id;
        state = getState();
    }
    
    public boolean getState() {
        try {
            URL obj = new URL("https://duluth.chandlerswift.com/5/light/status?id=" + Integer.toString(id));
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return !response.toString().equals("0");
        } catch (MalformedURLException ex) {
            Logger.getLogger(SwitchClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SwitchClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public boolean getCachedState() {
        return state;
    }
    
    public void setState(boolean new_state) {
        SwitchClient.log("Setting light " + Integer.toString(id));
        try {
            state = new_state;
            URL obj = new URL("https://duluth.chandlerswift.com/5/light/set?" + id + "=" + (state ? "255" : "0")); // 255 works for dimmable and not
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            //InputStream is = con.getInputStream();
            con.getInputStream();
        } catch (MalformedURLException ex) {
            Logger.getLogger(SwitchClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SwitchClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private boolean state;
    protected int id;
}
