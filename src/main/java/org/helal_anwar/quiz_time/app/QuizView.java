package org.helal_anwar.quiz_time.app;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class QuizView implements Initializable {
    @FXML
    private Label category;

    @FXML
    private Label level;

    @FXML
    private Label name;

    @FXML
    private Label number;

    @FXML
    private Label score;

    @FXML
    private Label total;

    @FXML
    private Label type;
    String s_category, s_level, s_name, s_number, s_score, s_total, s_type;

    public QuizView(String s_category, String s_level, String s_name, String s_number, String s_score, String s_total, String s_type) {
        this.s_category = s_category;
        this.s_level = s_level;
        this.s_name = s_name;
        this.s_number = s_number;
        this.s_score = s_score;
        this.s_total = s_total;
        this.s_type = s_type;


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        category.setText(category.getText() + s_category);
        level.setText(level.getText() + s_level);
        name.setText(s_name);
        number.setText(number.getText() + s_number);
        score.setText(s_score);
        total.setText(total.getText() + s_total);
        type.setText(type.getText() + s_type);
    }

    @Override
    public String toString() {
        return
                s_category + ',' +
                        s_level + ',' +
                        s_name + ',' +
                        s_number + ',' +
                        s_score + ',' +
                        s_total + ',' +
                        s_type
                ;
    }

}
