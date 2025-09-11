package org.helal_anwar.quiz_time.app;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import org.helal_anwar.quiz_time.app.question.Question;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class McqQuizController implements Initializable {
    @FXML
    private Label questionLabel;
    @FXML
    private RadioButton optionA, optionB, optionC, optionD;
    @FXML
    private ToggleGroup optionsGroup;
    @FXML
    Button submit;
    Question question;
    @FXML
    Label question_no;
    @FXML
    private VBox question_box;
    QuizTaker quizTaker;
    QuizDashboard quizDashboard;

    public McqQuizController(Question question, QuizTaker quizTaker, QuizDashboard quizDashboard) {
        this.question = question;
        this.quizTaker = quizTaker;
        this.quizDashboard = quizDashboard;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        question_no.setText("Question " + (question.getNum() + 1));
        System.out.println(question.getOptions());
        questionLabel.setText(question.getQuestion());
        optionA.setText(question.getOptions().getFirst());
        optionB.setText(question.getOptions().get(1));
        optionC.setText(question.getOptions().get(2));
        optionD.setText(question.getOptions().get(3));
    }


    @FXML
    private void handleSubmit() {
        RadioButton selected = (RadioButton) optionsGroup.getSelectedToggle();
        if (selected != null) {
            String answer = selected.getText();
            Label remark;
            if (question.getCorrectAnswers().contains(answer)) {
                remark = new Label("Correct");
                remark.setStyle("-fx-text-fill: green;");
                quizTaker.correctAnswer++;
            } else {
                remark = new Label("Incorrect");
                remark.setStyle("-fx-text-fill: red;");
                quizTaker.wrongAnswer++;
            }
            question_box.getChildren().add(remark);
            submit.setDisable(true);
            if (quizTaker.correctAnswer + quizTaker.wrongAnswer == quizTaker.questions.size()) {
                quizTaker.timeline.stop();
                var v = new FXMLLoader(QuizTime.class.getResource("overview.fxml"));
                v.setControllerFactory(c -> new Overview("" + quizTaker.correctAnswer,
                        "" + quizTaker.correctAnswer * 5,
                        "" + quizTaker.wrongAnswer,
                        quizTaker.timerLabel2.getText(),quizDashboard.modalPane));
                try {
                    quizDashboard.updateScore("" + (quizTaker.correctAnswer * 5));
                    quizDashboard.updateBestScore();
                    quizDashboard.modalPane.show(v.load());

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
