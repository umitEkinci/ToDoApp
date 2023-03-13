package controller;

import Database.DatabaseHandler;
import com.example.todoapp.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignupController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private CheckBox checkboxFemale;

    @FXML
    private CheckBox checkboxMale;

    @FXML
    private CheckBox checkboxOther;

    @FXML
    private TextField nameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button signupButton;

    @FXML
    private Button loginButton;

    @FXML
    private TextField surnameField;

    @FXML
    private TextField usernameField;

    @FXML
    void initialize() {

        DatabaseHandler databaseHandler = new DatabaseHandler();

        signupButton.setOnAction(event -> {
            createUser();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(HelloApplication.class.getResource("login.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            signupButton.getScene().setRoot(loader.getRoot());
        });

        loginButton.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(HelloApplication.class.getResource("login.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            loginButton.getScene().setRoot(loader.getRoot());
        });
    }

    private void createUser() {

        DatabaseHandler databaseHandler = new DatabaseHandler();

        String name = nameField.getText();
        String surname = surnameField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();

        String gender = "";
        if(checkboxFemale.isSelected()) {
            gender = "Female";
        }
        else if (checkboxMale.isSelected()) {
            gender = "Male";
        }
        else gender = "Other";

        User user = new User(name, surname, username, password, gender);

        databaseHandler.signUpUser(user);

    }
}
