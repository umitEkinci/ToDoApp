package controller;

import Database.DatabaseHandler;
import animations.Shaker;
import com.example.todoapp.HelloApplication;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Tasks;
import model.User;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController {

    private int userId;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane rootPane;

    private AnchorPane formPane;

    private AnchorPane signupPane;

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Button signUpButton;

    @FXML
    private TextField usernameTextField;

    private DatabaseHandler databaseHandler;

    @FXML
    void initialize() {
        databaseHandler = new DatabaseHandler();


        loginButton.setOnAction(event -> {

            String loginUsername = usernameTextField.getText().trim();
            String loginPassword = passwordTextField.getText().trim();

            User user = new User();
            user.setUsername(loginUsername);
            user.setPassword(loginPassword);

            ResultSet userRow = databaseHandler.getUser(user);

            int counter = 0;
            try {
                while (userRow.next()) {
                    counter++;
                    userId = userRow.getInt("UserId");
                }
                if (counter == 1) {
                    showAddItemFormScreen();
                    usernameTextField.setText("");
                    passwordTextField.setText("");

                }
                else {
                    Shaker usernameShaker = new Shaker(usernameTextField);
                    Shaker passwordShaker = new Shaker(passwordTextField);
                    passwordShaker.shake();
                    usernameShaker.shake();
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        });


        signUpButton.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("signup.fxml"));

            try {
                loader.load();
            }
            catch (IOException e) {
                e.printStackTrace();
            }

            signUpButton.getScene().setRoot(loader.getRoot());
        } );

    }


    private void showAddItemFormScreen() {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("addItemForm.fxml"));

        try {
            loader.load();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        loginButton.getScene().setRoot(loader.getRoot());

        AddItemFormController addItemFormController = loader.getController();
        addItemFormController.setUserId(userId);
    }


}
