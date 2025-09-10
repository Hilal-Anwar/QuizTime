package org.helal_anwar.quiz_time.app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ScoreTable implements Initializable {
    private final Connection connection;
    public TableView<UserScore> table;
    public TableColumn<UserScore, String> name;
    public TableColumn<UserScore, Integer> score;

    public ScoreTable(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        score.setCellValueFactory(new PropertyValueFactory<>("score"));
        ObservableList<UserScore> data = FXCollections.observableArrayList(
                loadData()
        );

        table.setItems(data);

    }

    public ObservableList<UserScore> loadData() {
        ArrayList<UserScore> userScores = new ArrayList<UserScore>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM user")) {

            while (rs.next()) {
                String userName = rs.getString("user_name");
                String bestScore = rs.getString("best_score");
                userScores.add(new UserScore(userName, Integer.parseInt(bestScore)));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return FXCollections.observableArrayList(userScores);
    }
}
