package model;

import controller.CellController;
import javafx.scene.control.Cell;

import java.sql.Timestamp;

public class Tasks {

    private int userId;
    private int taskId;
    private Timestamp DateCreated;
    private String Description;
    private String Task;

    private String Status;


    public Tasks() {

    }

    public Tasks(Timestamp dateCreated, String description, String task) {
        this.DateCreated = dateCreated;
        this.Description = description;
        this.Task = task;
    }

    public Timestamp getDateCreated() {
        return DateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.DateCreated = dateCreated;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        this.Description = description;
    }

    public String getTask() {
        return Task;
    }

    public void setTask(String task) {
        this.Task = task;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
