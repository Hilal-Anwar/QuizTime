package org.helal_anwar.quiz_time.app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class ScoreTable implements Initializable {
    public TableView<UserScore> table;
    public TableColumn<UserScore, String> name;
    public TableColumn<UserScore, Integer> score;
    public TableColumn<UserScore, String> duration;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        score.setCellValueFactory(new PropertyValueFactory<>("score"));
        duration.setCellValueFactory(new PropertyValueFactory<>("duration"));

        table.getColumns().addAll(name, score, duration);

        ObservableList<UserScore> data = FXCollections.observableArrayList(
                new UserScore("Alice", 85, "00:05:32"),
                new UserScore("Bob", 92, "00:04:47"),
                new UserScore("Charlie", 78, "00:06:15"),
                new UserScore("Diana", 88, "00:05:01")
        );

        table.setItems(data);

    }
}
