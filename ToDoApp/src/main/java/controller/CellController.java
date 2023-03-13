package controller;

import Database.Const;
import Database.DatabaseHandler;
import com.example.todoapp.HelloApplication;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import model.Tasks;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CellController extends ListCell <Tasks>{
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private Label dateLabel;

    @FXML
    private ImageView deleteButton;

    @FXML
    private Label descriptionLabel;

    @FXML
    private ImageView iconImageView;

    @FXML
    private Label taskLabel;

    @FXML
    private ImageView doneButton;

    @FXML
    private ImageView inProgressButton;

    @FXML
    private Label statusLabel;

    private int userId;
    @FXML
    private ImageView toDoStatusButton;

    private FXMLLoader fxmlLoader;

    private DatabaseHandler databaseHandler;

    @FXML
    void initialize() {
    }


    @Override
    public void updateItem(Tasks myTask, boolean empty) {
        super.updateItem(myTask, empty);

        if(empty || myTask == null) {
            setText(null);
            setGraphic(null);
        }
        else {
            if (fxmlLoader == null) {
                fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("cell.fxml"));
                fxmlLoader.setController(this);
                try {
                    fxmlLoader.load();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }


            taskLabel.setText(myTask.getTask());
            dateLabel.setText(myTask.getDateCreated().toString());
            descriptionLabel.setText(myTask.getDescription());
            statusLabel.setText(myTask.getStatus());

            if(myTask.getStatus().equals("ToDo")) {
                descriptionLabel.setStyle("-fx-background-color: #e57373;");
            }
            else if(myTask.getStatus().equals("In Progress...")) {
                descriptionLabel.setStyle("-fx-background-color: #f06292;");
            }
            else if(myTask.getStatus().equals("Done!")) {
                descriptionLabel.setStyle("-fx-background-color: #ba68c8;");
            }

            int taskId = myTask.getTaskId();

            deleteButton.setOnMouseClicked(event -> {
                databaseHandler = new DatabaseHandler();
                try {
                    databaseHandler.deleteTask(AddItemFormController.userId, taskId);
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                getListView().getItems().remove(getItem());
            });

            inProgressButton.setOnMouseClicked(event -> {

                statusLabel.setText("In Progress...");
                myTask.setStatus("In Progress...");
                descriptionLabel.setStyle("-fx-background-color: #f06292;");
                databaseHandler = new DatabaseHandler();
                try {
                    databaseHandler.updateStatus(statusLabel.getText().trim(), AddItemFormController.userId, taskId);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            });

            toDoStatusButton.setOnMouseClicked(event -> {

                statusLabel.setText("ToDo");
                myTask.setStatus("ToDo");
                descriptionLabel.setStyle("-fx-background-color: #e57373;");
                databaseHandler = new DatabaseHandler();
                try {
                    databaseHandler.updateStatus(statusLabel.getText().trim(), AddItemFormController.userId, taskId);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            });

            doneButton.setOnMouseClicked(event -> {

                statusLabel.setText("Done!");
                myTask.setStatus("Done!");
                descriptionLabel.setStyle("-fx-background-color: #ba68c8;");
                databaseHandler = new DatabaseHandler();
                try {
                    databaseHandler.updateStatus(statusLabel.getText().trim(), AddItemFormController.userId, taskId);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            });



            setText(null);
            setGraphic(rootAnchorPane);
        }


    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
