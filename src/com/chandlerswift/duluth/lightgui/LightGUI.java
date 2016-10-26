/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chandlerswift.duluth.lightgui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Chandler Swift
 */
public class LightGUI extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        main_light = new Light(1);
        
        Button btn = new Button("Turn Light " + (main_light.getState() != 0 ? "Off" : "On"));
        btn.setOnAction(a -> {
            btn.setDisable(true);
            main_light.setState(btn.getText().equals("Turn Light On"));
            btn.setText("Turn Light " + (btn.getText().equals("Turn Light On") ? "Off" : "On"));
            btn.setDisable(false);
        });
        
        StackPane root = new StackPane();
        root.getChildren().add(btn);
        
        Scene scene = new Scene(root, 300, 250);
        
        primaryStage.setTitle("Chandler's Light Control");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    private Light main_light;
    
    
}
