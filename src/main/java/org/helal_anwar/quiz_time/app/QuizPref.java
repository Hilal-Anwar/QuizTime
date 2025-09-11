package org.helal_anwar.quiz_time.app;

import atlantafx.base.controls.CustomTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QuizPref implements Initializable {

    @FXML
    private ComboBox<String> category_list;

    @FXML
    private ComboBox<String> level;

    @FXML
    private CustomTextField name;

    @FXML
    private Spinner<Double> no_question;

    @FXML
    private ComboBox<String> type;
    ListView<VBox> listView;
    LinkedHashMap<String, Long> category;
    Connection connection;
    String userName;
    ObservableList<VBox> list;

    public QuizPref(ListView<VBox> listView, LinkedHashMap<String, Long> category, DatabaseLoader db, String userName, ObservableList<VBox> list) {
        this.listView = listView;
        this.category = category;
        connection = db.getConnection();
        this.userName = userName;
        this.list = list;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        category_list.setItems(FXCollections.observableList(category.keySet().stream().toList()));
        level.setItems(FXCollections.observableList(new ArrayList<>(List.of("Hard", "Medium", "Easy"))));
        type.setItems(FXCollections.observableList(new ArrayList<>(List.of("Multiple Choice", "True/False"))));
        no_question.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(10, 100, 10)); // min, max, initialValue;

    }

    @FXML
    void apply(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(QuizResourceLoader.loadURL("quiz_view.fxml"));
        QuizView quizView = new QuizView(category_list.getSelectionModel().getSelectedItem()
                , level.getSelectionModel().getSelectedItem(), name.getText(), "" + (list.size() + 1),
                "0", no_question.getValueFactory().getValue().toString(), type.getSelectionModel().getSelectedItem());
        var x = quizView.toString().split(",");
        updateQuizDB(x);
        fxmlLoader.setControllerFactory(c -> quizView);
        try {
            VBox box = fxmlLoader.load();
            VBox.setMargin(box, new Insets(10));
            list.add(box);
            listView.setItems(list);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void updateQuizDB(String[] x) {
        String sql = "UPDATE user SET quizzes = ? WHERE user_name = ?";

        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            var quizzes = getQuizzes(userName);
            quizzes.add(x);
            pst.setString(1, getArrayAsString(quizzes));
            pst.setString(2, userName);
            int affectedRows = pst.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    static String getArrayAsString(ArrayList<String[]> array) {
        StringBuilder s = new StringBuilder("[");
        for (int i = 0; i < array.size() - 1; i++) {
            s.append(getArrayAsString(array.get(i))).append(",");
        }
        s.append(getArrayAsString(array.getLast())).append("]");
        return s.toString();
    }

    static String getArrayAsString(String[] array) {
        StringBuilder s = new StringBuilder("[");
        for (int i = 0; i < array.length - 1; i++) {
            s.append(array[i].trim()).append(",");
        }
        s.append(array[array.length - 1].trim()).append("]");
        return s.toString();
    }

    private ArrayList<String[]> convertToArray(String str) {
        var list = new ArrayList<String[]>();
        Pattern pattern = Pattern.compile("\\[(.*?)]");
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            String group = matcher.group(1);
            String[] elements = getArrayAsString(group.split(",")).
                    replace("[", "").
                    replace("]", "").split(",");
            list.add(elements);
        }
        return list;
    }

    private ArrayList<String[]> getQuizzes(String userName) {
        ArrayList<String[]> list = new ArrayList<>();
        String sql = "SELECT quizzes FROM user WHERE user_name = ?";

        try (PreparedStatement pst = connection.prepareStatement(sql)) {

            pst.setString(1, userName);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                var app = rs.getString("quizzes");
                if (app != null) {
                    list = convertToArray(app);
                }

            } else {
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @FXML
    void cancel(ActionEvent event) {

    }
}
