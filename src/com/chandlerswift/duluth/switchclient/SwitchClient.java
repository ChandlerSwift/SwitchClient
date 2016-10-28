/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chandlerswift.duluth.switchclient;

import java.awt.Rectangle;
import java.util.Optional;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Pair;

/**
 *
 * @author Chandler Swift
 */
public class SwitchClient extends Application {
    
    private final int NUM_LIGHTS = 5;
    private final String descriptions[] = {
        "ESP LED",
        "Main Light",
        "Red Channel",
        "Green Channel",
        "Blue Channel"
    };
    
    @Override
    public void start(Stage primaryStage) {
        
        log = "";
        
        // Display Loading Screen
        //showLoading(primaryStage);
        
        // Set up lights
        lights = new Light[NUM_LIGHTS];
        for (int i = 0; i < NUM_LIGHTS; i++) {
            lights[i] = new Light(i);
        }
        
        // Build Image Display
        Image lightMask = new Image(SwitchClient.class.getResourceAsStream("light_mask.png")); // TODO: check fix for JAR?
        ImageView lightImage = new ImageView();
        lightImage.setImage(lightMask);
        lightImage.setFitWidth(100);
        lightImage.setPreserveRatio(true);
        lightImage.setSmooth(true);
        lightImage.setCache(true);
        
        GridPane contentContainer = new GridPane();
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(50);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(50);
        contentContainer.getColumnConstraints().addAll(column1, column2); // each get 50% of width
        
        

        StackPane imageContainer = new StackPane();
        mainIndicator = new Circle(75, (lights[1].getState() ? Color.YELLOW : Color.DARKGRAY));
        imageContainer.getChildren().addAll(mainIndicator, lightImage);
        contentContainer.add(makeButtonPanel(), 0, 1);
        contentContainer.add(imageContainer, 1, 1);  // column=3 row=1
        contentContainer.setValignment(imageContainer, VPos.CENTER);
        BorderPane root = new BorderPane();
        root.setTop(makeMenuBar());
        root.setCenter(contentContainer);
        
        Scene scene = new Scene(root, 500, 300);
        primaryStage.setTitle("Chandler's Light Control");
        primaryStage.getIcons().addAll(
                new Image(SwitchClient.class.getResourceAsStream("icon_16.png")),
                new Image(SwitchClient.class.getResourceAsStream("icon_48.png")));
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    private MenuBar makeMenuBar() {
        // Options Menu
        Menu optionsMenu = new Menu("Options");
        MenuItem reauth = new MenuItem("Reauthenticate");
        reauth.setOnAction(e -> auth());
        MenuItem lightSettings = new MenuItem("Light Settings");
        lightSettings.setOnAction(e -> showLightSettings());
        optionsMenu.getItems().addAll(reauth, lightSettings);
        
        // Help Menu
        Menu helpMenu = new Menu("Help");
        MenuItem showLog = new MenuItem("Show Log");
        showLog.setOnAction(e -> showLog());
        MenuItem about = new MenuItem("About");
        about.setOnAction(e -> showInfo());
        helpMenu.getItems().addAll(showLog, new SeparatorMenuItem(), about);

        // Create menu bar and add the menus.
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(optionsMenu, helpMenu);
        return menuBar;
    }
    
    private VBox makeButtonPanel() {
        VBox buttonContainer = new VBox(10);
        buttonContainer.setPadding(new Insets(15, 12, 15, 12));
        buttonContainer.setMaxWidth(250);
        Button btns[] = new Button[NUM_LIGHTS];
        for (int i = 0; i < NUM_LIGHTS; i++) {
            btns[i] = new Button(descriptions[i] + (lights[i].getCachedState() ? " Off" : " On"));
            final int j = i;
            btns[i].setOnAction(a -> {
                btns[j].setDisable(true);
                lights[j].setState(!lights[j].getCachedState());
                btns[j].setText(descriptions[j] + (lights[j].getCachedState() ? " Off" : " On"));
                btns[j].setDisable(false);
            });
            btns[i].setMaxWidth(Double.MAX_VALUE);
            buttonContainer.getChildren().add(btns[i]);     
        }
        btns[1].setOnAction(e -> {
                btns[1].setDisable(true);
                lights[1].setState(!lights[1].getCachedState());
                btns[1].setText(descriptions[1] + (lights[1].getCachedState() ? " Off" : " On"));
                btns[1].setDisable(false);
                mainIndicator.setFill(lights[1].getCachedState() ? Color.YELLOW : Color.DARKGRAY);
        });
        return buttonContainer;
    }
    
    private void auth() {
        // Create the custom dialog.
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Login Dialog");
        dialog.setHeaderText("Please Authenticate");

        // Set the icon (must be included in the project).
        dialog.setGraphic(new ImageView(this.getClass().getResource("login.png").toString()));

        // Set the button types.
        ButtonType loginButtonType = new ButtonType("Login", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        

        TextField username = new TextField();
        username.setPromptText("Username");
        PasswordField password = new PasswordField();
        password.setPromptText("Password");

        grid.add(new Label("Username:"), 0, 0);
        grid.add(username, 1, 0);
        grid.add(new Label("Password:"), 0, 1);
        grid.add(password, 1, 1);

        // Enable/Disable login button depending on whether a username was entered.
        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

        // Do some validation (using the Java 8 lambda syntax).
        username.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);

        // Request focus on the username field by default.
        Platform.runLater(() -> username.requestFocus());

        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(username.getText(), password.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(usernamePassword -> {
            log("Username=" + usernamePassword.getKey() + ", Password=" + usernamePassword.getValue());
        });
    }
    
    private static void showLightSettings() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Settings");
        alert.setHeaderText("Chandler");
        alert.setContentText("...");

        alert.showAndWait();
    }
    
    private static void showInfo() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("Chandler's Light - Java UI");
        alert.setContentText("This is a Java implementation of the light "
                + "switch API.");

        alert.showAndWait();
    }
    
    private static void showLog() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("About ");
        alert.setHeaderText("Chandler's Light - Java UI");
        alert.setContentText(log);

        alert.showAndWait();
    }
    
    private void showLoading(Stage primaryStage) {
        log("Loading");
        StackPane root = new StackPane();
        Label loading = new Label("Loading...");
        root.getChildren().add(loading);
        Scene scene = new Scene(root, 500, 300);
        primaryStage.setTitle("Chandler's Light Control");
        primaryStage.getIcons().addAll(
                new Image(SwitchClient.class.getResourceAsStream("icon_16.png")),
                new Image(SwitchClient.class.getResourceAsStream("icon_48.png")));
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public static void log(String... strings) {
        for (String string : strings) {
            log += new java.util.Date();
            log += ": ";
            log += string;
            log += "\r\n";
        }
    }
    
    @Override
    public void stop() {
        System.out.println(log);
    }
    
    private static String log;
    private Light lights[];
    private Circle mainIndicator;
    private Rectangle rgbIndicator;
    private Light mainLight;
    private BasicAuth auth;
    
}
