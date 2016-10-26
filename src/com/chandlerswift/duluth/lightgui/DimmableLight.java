package com.chandlerswift.duluth.lightgui;

///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package lightgui;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
///**
// *
// * @author Chandler Swift
// */
//public class DimmableLight extends Light {
//    
//    public DimmableLight(int id) {
//        super(id);
//        brightness = getState();
//    }
//        
//    public void setBrightness(int new_brightness) {
//        try {
//            brightness = new_brightness;
//            URL obj = new URL("https://duluth.chandlerswift.com/5/light/set?" + id + "=" + brightness);
//            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
//        } catch (MalformedURLException ex) {
//            Logger.getLogger(LightGUI.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(LightGUI.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//    
//    public int getRecordedBrightness() {
//        return brightness;
//    }
//    
//    private int brightness;
//    
//}
