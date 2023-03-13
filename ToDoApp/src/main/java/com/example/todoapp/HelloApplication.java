package com.example.todoapp;

import Database.DatabaseHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("list.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("addItem.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        URL iconURL = HelloApplication.class.getResource("icon.png");
        stage.getIcons().add(new Image(HelloApplication.class.getResourceAsStream("/assets/icon.png")));
        stage.setTitle("ToDo");
        stage.setScene(scene);
        stage.show();



    }
}