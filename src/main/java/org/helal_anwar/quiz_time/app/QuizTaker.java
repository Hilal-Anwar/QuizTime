package org.helal_anwar.quiz_time.app;

import atlantafx.base.theme.Styles;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.helal_anwar.quiz_time.app.question.Question;

import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class QuizTaker implements Initializable {
    public Button reset;
    public Button quit;
    @FXML
    private VBox question_box;
    @FXML
    Label timerLabel2;
    ArrayList<VBox> questionList = new ArrayList<>();
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    int index = 0;
    public int totalSeconds = 0;
    Timeline timeline;
    int correctAnswer = 0;
    int wrongAnswer = 0;
    List<Question> questions;
    QuizDashboard quizDashboard;

    public QuizTaker(List<Question> questions, QuizDashboard quizDashboard) {
        this.questions = questions;
        this.quizDashboard = quizDashboard;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        questionList.clear();
        if (questions.isEmpty())
            questions = quizDashboard.defaultQuestions;
        for (Question question : questions) {
            FXMLLoader v;
            if (question.getOptions().size() > 2) {
                v = new FXMLLoader(QuizTime.class.getResource("mcq_questions.fxml"));
                v.setControllerFactory(_ -> new McqQuizController(question, this, quizDashboard));
            } else {
                v = new FXMLLoader(QuizTime.class.getResource("true_false_questions.fxml"));
                v.setControllerFactory(_ -> new TrueOrFalseQuizController(question, this, quizDashboard));
            }
            try {
                VBox qus = v.load();
                VBox.setMargin(qus, new Insets(10));
                questionList.add(qus);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        reset.getStyleClass().addAll(Styles.BUTTON_CIRCLE, Styles.FLAT, Styles.SUCCESS);
        quit.getStyleClass().addAll(Styles.BUTTON_CIRCLE, Styles.FLAT, Styles.DANGER);
        System.out.println(questionList);
        question_box.getChildren().set(0, questionList.getFirst());
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            totalSeconds++;
            timerLabel2.setText(formatTime(totalSeconds));

            if (totalSeconds <= 0) {
                timeline.stop();
                timerLabel2.setText(formatTime(totalSeconds));
            }
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    @FXML
    void next() {
        if (index < questions.size() - 1)
            question_box.getChildren().set(0, questionList.get(++index));
    }

    @FXML
    void previous() {
        if (index > 0)
            question_box.getChildren().set(0, questionList.get(--index));
    }

    private String formatTime(int seconds) {
        LocalTime time = LocalTime.MIDNIGHT.plusSeconds(seconds);
        return time.format(timeFormatter);
    }

    @FXML
    void onClose() {
        quizDashboard.modalPane.hide();
        quizDashboard.questions = null;
    }
}
