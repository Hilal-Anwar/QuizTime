package org.helal_anwar.quiz_time.app;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import org.helal_anwar.quiz_time.app.question.Question;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TrueOrFalseQuizController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        question_no.setText("Question " + (question.getNum() + 1));
        System.out.println(question.getOptions());
        questionLabel.setText(question.getQuestion());
        optionA.setText(question.getOptions().getFirst());
        optionB.setText(question.getOptions().get(1));
    }

    public Label questionLabel;
    public Label question_no;
    public RadioButton optionA;
    public RadioButton optionB;
    public VBox question_box;
    public ToggleGroup optionsGroup;
    public Button submit;
    Question question;
    QuizTaker quizTaker;
    QuizDashboard quizDashboard;
    public TrueOrFalseQuizController(Question question, QuizTaker quizTaker, QuizDashboard quizDashboard) {
        this.question = question;
        this.quizTaker = quizTaker;
        this.quizDashboard = quizDashboard;
    }

    public void handleSubmit(ActionEvent actionEvent) {
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
