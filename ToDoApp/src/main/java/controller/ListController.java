package controller;

import Database.DatabaseHandler;
import com.example.todoapp.HelloApplication;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.media.AudioClip;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import model.Tasks;

import javax.print.attribute.standard.MediaName;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.scene.media.Media;

public class ListController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField descriptionFieldList;

    @FXML
    private Button saveTaskButtonList;

    @FXML
    private TextField taskFieldList;

    @FXML
    private Button pauseButton;

    @FXML
    private Button startButton;

    @FXML
    private Button stopButton;

    @FXML
    private Label timerLabel;

    @FXML
    private Button shortBreakPauseButton;

    @FXML
    private Button shortBreakStartButton;

    @FXML
    private Button shortBreakStopButton;

    @FXML
    private Label shortBreakTimerLabel;

    @FXML
    private Button longBreakPauseButton;

    @FXML
    private Button longBreakStartButton;

    @FXML
    private Button longBreakStopButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Label longBreakTimerLabel;

    @FXML
    private ListView<Tasks> taskList;

    @FXML
    private ImageView pomodoroIcon;

    @FXML
    private Label pomodoroCount;

    @FXML
    private Button resetCounterButton;

    int pomodoroNumber = 0;

    URL buzzer;



    DatabaseHandler databaseHandler;


    @FXML
    void addTask(ActionEvent event) {

    }

    Timeline timeline;
    Timeline timelineShortBreak;
    Timeline timelineLongBreak;
    LocalTime time = LocalTime.parse("00:00");
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("mm:ss");

    private AudioClip audioClip;

    JFrame frame;

    private ObservableList<Tasks> tasks;

    private ObservableList<Tasks> refreshedTasks;



    @FXML
    void initialize() throws SQLException {

        tasks = FXCollections.observableArrayList();

        databaseHandler = new DatabaseHandler();
        ResultSet resultSet = databaseHandler.getTasksByUser(AddItemFormController.userId);

        while(resultSet.next()) {
            Tasks tasks1 = new Tasks();
            tasks1.setTaskId(resultSet.getInt("Id"));
            tasks1.setTask(resultSet.getString("Task"));
            tasks1.setDateCreated(resultSet.getTimestamp("DateCreated"));
            tasks1.setDescription(resultSet.getString("Description"));
            tasks1.setStatus(resultSet.getString("Status"));

            tasks.addAll(tasks1);
        }

        taskList.setItems(tasks);
        taskList.setCellFactory(CellController -> new CellController());

        saveTaskButtonList.setOnAction(event -> {
            addNewTask();
        });



        startButton.setOnAction(event -> {
            pomodoroTime();
            time = LocalTime.of(0,25,00);
            startTimer();
            shortBreakStartButton.setDisable(true);
            shortBreakPauseButton.setDisable(true);
            shortBreakStopButton.setDisable(true);
            longBreakStartButton.setDisable(true);
            longBreakPauseButton.setDisable(true);
            longBreakStopButton.setDisable(true);
        });

        pauseButton.setOnAction(event -> {
            pauseTimer();
        });

        stopButton.setOnAction(event -> {
            stopTimer();
            pauseButton.setText("Pause");
            shortBreakStartButton.setDisable(false);
            shortBreakPauseButton.setDisable(false);
            shortBreakStopButton.setDisable(false);
            longBreakStartButton.setDisable(false);
            longBreakPauseButton.setDisable(false);
            longBreakStopButton.setDisable(false);
            timerLabel.setText("25:00");
        });

        shortBreakStartButton.setOnAction(event -> {
            pomodoroTime();
            time = LocalTime.of(0,5,00);
            startShortBreakTimer();
            startButton.setDisable(true);
            pauseButton.setDisable(true);
            stopButton.setDisable(true);
            longBreakStartButton.setDisable(true);
            longBreakPauseButton.setDisable(true);
            longBreakStopButton.setDisable(true);
        });

        shortBreakPauseButton.setOnAction(event -> {
            pauseShortBreakTimer();
        });

        shortBreakStopButton.setOnAction(event -> {
            stopShortBreakTimer();
            shortBreakPauseButton.setText("Pause");
            startButton.setDisable(false);
            pauseButton.setDisable(false);
            stopButton.setDisable(false);
            longBreakStartButton.setDisable(false);
            longBreakPauseButton.setDisable(false);
            longBreakStopButton.setDisable(false);
            shortBreakTimerLabel.setText("05:00");
        });

        longBreakStartButton.setOnAction(event -> {
            pomodoroTime();
            time = LocalTime.of(0,15,00);
            startLongBreakTimer();
            startButton.setDisable(true);
            pauseButton.setDisable(true);
            stopButton.setDisable(true);
            shortBreakStartButton.setDisable(true);
            shortBreakPauseButton.setDisable(true);
            shortBreakStopButton.setDisable(true);
        });

        longBreakPauseButton.setOnAction(event -> {
            pauseLongBreakTimer();
        });

        longBreakStopButton.setOnAction(event -> {
            stopLongBreakTimer();
            longBreakPauseButton.setText("Pause");
            startButton.setDisable(false);
            pauseButton.setDisable(false);
            stopButton.setDisable(false);
            shortBreakStartButton.setDisable(false);
            shortBreakPauseButton.setDisable(false);
            shortBreakStopButton.setDisable(false);
            longBreakTimerLabel.setText("15:00");
        });

        resetCounterButton.setOnAction(event -> {
            pomodoroNumber = 0;
            pomodoroCount.setText(String.valueOf(pomodoroNumber));
        });

        logoutButton.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(HelloApplication.class.getResource("login.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            logoutButton.getScene().setRoot(loader.getRoot());
        });

    }

    public void pomodoroTime() {
        timeline = new Timeline(new KeyFrame(Duration.millis(1000), event -> decrementTime()));
        timeline.setCycleCount(Animation.INDEFINITE);

        timelineShortBreak = new Timeline(new KeyFrame(Duration.millis(1000), event -> decrementShortBreakTime()));
        timelineShortBreak.setCycleCount(Animation.INDEFINITE);

        timelineLongBreak = new Timeline(new KeyFrame(Duration.millis(1000), event -> decrementLongBreakTime()));
        timelineLongBreak.setCycleCount(Animation.INDEFINITE);
    }


    public void addNewTask () {

            if(!taskFieldList.getText().equals("") || !descriptionFieldList.getText().equals("")) {

                Tasks myNewTask = new Tasks();
                Calendar calendar = Calendar.getInstance();

                java.sql.Timestamp timestamp = new java.sql.Timestamp(calendar.getTimeInMillis());
                myNewTask.setUserId(AddItemFormController.userId);
                myNewTask.setTask(taskFieldList.getText().trim());
                myNewTask.setDescription(descriptionFieldList.getText().trim());
                myNewTask.setDateCreated(timestamp);
                myNewTask.setStatus("ToDo");


                databaseHandler.insertTask(myNewTask);

                taskFieldList.setText("");
                descriptionFieldList.setText("");

                try {
                    initialize();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

    }

    private void decrementTime() {
        time = time.minusSeconds(100);
        timerLabel.setText(time.format(dtf));
        if(timerLabel.getText().equals("00:00")) {
            stopTimer();
            shortBreakStartButton.setDisable(false);
            shortBreakPauseButton.setDisable(false);
            shortBreakStopButton.setDisable(false);
            longBreakStartButton.setDisable(false);
            longBreakPauseButton.setDisable(false);
            longBreakStopButton.setDisable(false);
            timerLabel.setText("25:00");


            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alarm");
            alert.setHeaderText("Pomodoro is done!");
            alert.setContentText("Time to break!");
            alert.show();
            alarmSound();

            pomodoroNumber++;
            pomodoroCount.setText(String.valueOf(pomodoroNumber));


            //audioClip = new AudioClip(Paths.get("src/main/resources/sounds/microsoft-windows-10-alarm-1.wav").toUri().toString());
            //audioClip.play();
        }
    }
    private void decrementShortBreakTime() {
        time = time.minusSeconds(1);
        shortBreakTimerLabel.setText(time.format(dtf));
        if(shortBreakTimerLabel.getText().equals("00:00")) {
            stopShortBreakTimer();
            startButton.setDisable(false);
            pauseButton.setDisable(false);
            stopButton.setDisable(false);
            longBreakStartButton.setDisable(false);
            longBreakPauseButton.setDisable(false);
            longBreakStopButton.setDisable(false);
            shortBreakTimerLabel.setText("05:00");


            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alarm");
            alert.setHeaderText("Short break is done!");
            alert.setContentText("Time to work!");
            alert.show();
            alarmSound();


            //audioClip = new AudioClip(Paths.get("src/main/resources/sounds/microsoft-windows-10-alarm-1.wav").toUri().toString());
            //audioClip.play();

        }
    }

    private void decrementLongBreakTime() {
        time = time.minusSeconds(1);
        longBreakTimerLabel.setText(time.format(dtf));
        if(longBreakTimerLabel.getText().equals("00:00")) {
            stopLongBreakTimer();
            startButton.setDisable(false);
            pauseButton.setDisable(false);
            stopButton.setDisable(false);
            shortBreakStartButton.setDisable(false);
            shortBreakPauseButton.setDisable(false);
            shortBreakStopButton.setDisable(false);
            longBreakTimerLabel.setText("15:00");


            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alarm");
            alert.setHeaderText("Long break is done!");
            alert.setContentText("Time to work!");
            alert.show();
            alarmSound();

            //audioClip = new AudioClip(Paths.get("src/main/resources/sounds/microsoft-windows-10-alarm-1.wav").toUri().toString());
            //audioClip.play();

        }
    }

    private void startTimer() {
        timeline.play();
        startButton.setDisable(true);
    }

    private void startShortBreakTimer() {
        timelineShortBreak.play();
        shortBreakStartButton.setDisable(true);
    }

    private void startLongBreakTimer() {
        timelineLongBreak.play();
        longBreakStartButton.setDisable(true);
    }

    private void pauseTimer() {
        if(timeline.getStatus().equals(Animation.Status.PAUSED)) {
            timeline.play();
            pauseButton.setText("Pause");
        }
        else if(timeline.getStatus().equals(Animation.Status.RUNNING)) {
            timeline.pause();
            pauseButton.setText("Continue");
        }
    }

    private void pauseShortBreakTimer() {
        if(timelineShortBreak.getStatus().equals(Animation.Status.PAUSED)) {
            timelineShortBreak.play();
            shortBreakPauseButton.setText("Pause");
        }
        else if(timelineShortBreak.getStatus().equals(Animation.Status.RUNNING)) {
            timelineShortBreak.pause();
            shortBreakPauseButton.setText("Continue");
        }
    }

    private void pauseLongBreakTimer() {
        if(timelineLongBreak.getStatus().equals(Animation.Status.PAUSED)) {
            timelineLongBreak.play();
            longBreakPauseButton.setText("Pause");
        }
        else if(timelineLongBreak.getStatus().equals(Animation.Status.RUNNING)) {
            timelineLongBreak.pause();
            longBreakPauseButton.setText("Continue");
        }
    }

    private void stopTimer() {
        if(startButton.isDisable()) {
            timeline.stop();
            startButton.setDisable(false);
            time = LocalTime.parse("00:00");
            timerLabel.setText(time.format(dtf));
        }
    }

    private void stopShortBreakTimer() {
        if(shortBreakStartButton.isDisable()) {
            timelineShortBreak.stop();
            shortBreakStartButton.setDisable(false);
            time = LocalTime.parse("00:00");
            shortBreakTimerLabel.setText(time.format(dtf));
        }
    }

    private void stopLongBreakTimer() {
        if(longBreakStartButton.isDisable()) {
            timelineLongBreak.stop();
            longBreakStartButton.setDisable(false);
            time = LocalTime.parse("00:00");
            longBreakTimerLabel.setText(time.format(dtf));
        }
    }

    public static synchronized void alarmSound() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass().getResource("/sounds/microsoft-windows-10-alarm-1.wav")));
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioInputStream);
                    clip.start();

                } catch (LineUnavailableException e) {
                    throw new RuntimeException(e);
                } catch (UnsupportedAudioFileException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

    }



}
