/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lightgui;

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
        id = new_id;
        state = (getState() != 0);
    }
    
    public int getState(){
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
            
            return Integer.parseInt(response.toString());
        } catch (MalformedURLException ex) {
            Logger.getLogger(LightGUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LightGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    
    public void setState(boolean new_state) {
        try {
            state = new_state;
            URL obj = new URL("https://duluth.chandlerswift.com/5/light/set?1=" + (state ? "1" : "0"));
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            InputStream is = con.getInputStream();
        } catch (MalformedURLException ex) {
            Logger.getLogger(LightGUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LightGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private boolean state;
    protected int id;
}
