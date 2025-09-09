package org.helal_anwar.quiz_time.app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class Overview implements Initializable {
    public Label correct;
    public Label score;
    public Label wrong;
    public Label duration;
    public String correct_s, score_s, wrong_s, duration_s;

    public Overview(String correct_s, String score_s, String wrong_s, String duration_s) {
        this.correct_s = correct_s;
        this.score_s = score_s;
        this.wrong_s = wrong_s;
        this.duration_s = duration_s;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        correct.setText(correct_s);
        score.setText(score_s);
        wrong.setText(wrong_s);
        duration.setText(duration_s);
    }
    @FXML
    void onClose() {

    }
}
