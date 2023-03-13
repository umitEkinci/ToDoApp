package controller;

import Database.DatabaseHandler;
import com.example.todoapp.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Tasks;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.ResourceBundle;

public class AddItemFormController {

    public static int userId;

    private DatabaseHandler databaseHandler;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField descriptionField;

    @FXML
    private Button saveTaskButton;

    @FXML
    private TextField taskField;

    @FXML
    private Label successLabel;

    @FXML
    private Button todosButton;


    @FXML
    void initialize() {

        databaseHandler = new DatabaseHandler();
        Tasks tasks = new Tasks();

        AddItemFormController.userId = getUserId();

        saveTaskButton.setOnAction(event -> {


            Calendar calendar = Calendar.getInstance();
            java.sql.Timestamp timestamp = new java.sql.Timestamp(calendar.getTimeInMillis());

            String taskText = taskField.getText().trim();
            String taskDescription = descriptionField.getText().trim();

            if (!taskText.equals("") || !taskDescription.equals("")) {

                tasks.setUserId(userId);
                tasks.setDateCreated(timestamp);
                tasks.setDescription(taskDescription);
                tasks.setTask(taskText);
                tasks.setStatus("ToDo");

                databaseHandler.insertTask(tasks);

                successLabel.setVisible(true);



                taskField.setText("");
                descriptionField.setText("");




            }
            else {
                System.out.println(("Nothing added!"));
            }

        });

        todosButton.setOnAction(event1 -> {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(HelloApplication.class.getResource("list.fxml"));

            try {
                fxmlLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            todosButton.getScene().setRoot(fxmlLoader.getRoot());
        });

    }


    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
        System.out.println(userId);
    }
}
