module com.example.todoapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.microsoft.sqlserver.jdbc;
    requires java.desktop;
    requires javafx.media;


    opens com.example.todoapp to javafx.fxml;
    exports com.example.todoapp;
    exports controller;
    opens controller to javafx.fxml;
}