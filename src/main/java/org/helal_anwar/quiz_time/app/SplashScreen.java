package org.helal_anwar.quiz_time.app;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.helal_anwar.quiz_time.app.question.Question;
import org.helal_anwar.quiz_time.app.question.QuizService;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ResourceBundle;

public class SplashScreen implements Initializable {
    DatabaseLoader db;
    EmptyLogin emptyLogin;
    Stage stage;
    List<Question> defaultQuestions;
    @FXML
    private ProgressBar progress;
    Timeline timeline;
    FXMLLoader fxmlLoader;
    LinkedHashMap<String,Long> categoryMap;
    public SplashScreen(Stage stage) {
        this.stage = stage;
        db = new DatabaseLoader("quiztime");
        db.createTable();
        new Thread(() -> {
            try {
                categoryMap = QuizService.fetchCategory();
                defaultQuestions = QuizService.fetchDefaultQuestions();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginScreen();
        startTimer();
    }

     void loginScreen() {
        fxmlLoader = new FXMLLoader(QuizResourceLoader.loadURL("login_setup.fxml"));
        emptyLogin = new EmptyLogin(this);
        fxmlLoader.setControllerFactory(_ -> emptyLogin);
    }
    void logOutScreen() {
        fxmlLoader = new FXMLLoader(QuizResourceLoader.loadURL("login_setup.fxml"));
        emptyLogin = new EmptyLogin(this);
        fxmlLoader.setControllerFactory(_ -> emptyLogin);
        try {
            stage.setScene(new Scene(fxmlLoader.load()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void startTimer() {
        timeline = new Timeline(new KeyFrame(Duration.millis(5), e -> {
            progress.setProgress(progress.getProgress() + 0.0007);
            if (categoryMap != null) {
                stage.close();
                System.out.println(categoryMap);
                stage = new Stage();
                stage.setTitle("Quiz Time");
                //stage.setResizable(false);
                stage.getIcons().add(new Image(QuizResourceLoader.loadStream("logo.png")));
                try {
                    stage.setScene(new Scene(fxmlLoader.load()));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                stage.show();
                timeline.stop();
            } else if (progress.getProgress() > 1) {
                timeline.stop();
                showAlert();
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
    public void showAlert() {
        var alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("No internet connection");
        alert.setContentText("Time out");
        alert.show();
    }
}
