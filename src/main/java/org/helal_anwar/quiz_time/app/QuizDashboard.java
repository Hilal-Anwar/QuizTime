package org.helal_anwar.quiz_time.app;

import atlantafx.base.controls.ModalPane;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.helal_anwar.quiz_time.app.question.Question;
import org.helal_anwar.quiz_time.app.question.QuizService;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QuizDashboard implements Initializable {
    List<Question> questions;
    List<Question> defaultQuestions;
    private final SplashScreen
            splashScreen;
    @FXML
    private Button quit;
    @FXML
    private Button reset;
    QuizPref quizPref;
    String userName;
    Timeline timeline;
    @FXML
    ListView<VBox> listView;
    LinkedHashMap<String, Long> category;
    ObservableList<VBox> list = FXCollections.observableArrayList();
    @FXML
    VBox loadingPane;
    String selectedQuestionName;

    public QuizDashboard(LinkedHashMap<String, Long> category, DatabaseLoader db,
                         String userName, List<Question> defaultQuestions, SplashScreen splashScreen) {
        this.category = category;
        this.userName = userName;
        this.db = db;
        this.defaultQuestions = defaultQuestions;

        this.splashScreen = splashScreen;
    }

    private void loadTheQuiz() {
        var listQuiz = getQuizzes(userName).values();
        for (var quiz : listQuiz) {
            FXMLLoader fxmlLoader = new FXMLLoader(QuizResourceLoader.loadURL("quiz_view.fxml"));
            QuizView quizView = new QuizView(quiz[0].trim()
                    , quiz[1].trim(), quiz[2].trim(), quiz[3].trim(),
                    quiz[4].trim(), quiz[5].trim(), quiz[6].trim());
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

    }

    @FXML
    ModalPane modalPane;

    DatabaseLoader db;

    @FXML
    void showNavigation() {
        var v = new FXMLLoader(QuizTime.class.getResource("navigation_drawer.fxml"));
        try {
            v.setControllerFactory(c -> new Navigation(userName, modalPane,splashScreen));
            modalPane.show(v.load());
            modalPane.usePredefinedTransitionFactories(Side.TOP);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadTheQuiz();
        listView.getSelectionModel().selectedItemProperty().
                addListener((observable,
                             oldValue, newValue) ->
                {
                    selectedQuestionName = getValue(newValue, "name");
                    String no_of_questions = "" + (Double.valueOf(getValue(newValue, "total").trim())).intValue();
                    String cat = "" + category.get(getValue(newValue, "category"));
                    String level = getValue(newValue, "level").trim().toLowerCase();
                    String type = getValue(newValue, "type").contains("Multiple Choice") ? "multiple" : "boolean";
                    new Thread(() -> {
                        try {
                            questions = QuizService.fetchQuestions(cat, level, type, no_of_questions);
                            loadingPane.setVisible(true);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }).start();
                    timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
                        if (questions != null) {
                            loadingPane.setVisible(false);
                            quizGenerator();
                            timeline.stop();
                        }
                    }));

                    timeline.setCycleCount(Timeline.INDEFINITE);
                    timeline.play();
                });
    }

    private void quizGenerator() {
        var v = new FXMLLoader(QuizTime.class.getResource("quiz_taker.fxml"));
        try {
            v.setControllerFactory(c -> new QuizTaker(questions, this));
            modalPane.show(v.load());
            modalPane.usePredefinedTransitionFactories(Side.TOP);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getValue(VBox newValue, String val) {
        return ((Label) newValue.lookup("#" + val)).getText().substring(((Label) newValue.lookup("#" + val)).getText().indexOf(":") + 1).trim();
    }

    void updateBestScore() {
        String sql = "UPDATE user SET best_score = ? WHERE user_name = ?";
        try (PreparedStatement pst = db.getConnection().prepareStatement(sql)) {
            var quizzes = getQuizzes(userName).values();
            int maxScore = Integer.parseInt(quizzes.iterator().next()[4].trim());
            for (var quiz : quizzes) {
                maxScore = Math.max(maxScore, Integer.parseInt(quiz[4].trim()));
            }
            pst.setString(1, String.valueOf(maxScore));
            pst.setString(2, userName);
            int affectedRows = pst.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void updateScore(String score) {
        String sql = "UPDATE user SET quizzes = ? WHERE user_name = ?";

        try (PreparedStatement pst = db.getConnection().prepareStatement(sql)) {

            var map = getQuizzes(userName);
            var quizzes = map.get(selectedQuestionName);
            quizzes[4] = score.trim();

            pst.setString(1, QuizPref.getArrayAsString(new ArrayList<>(map.values().stream().toList())));
            pst.setString(2, userName);
            int affectedRows = pst.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    void addNewQuiz() {
        var v = new FXMLLoader(QuizTime.class.getResource("quiz_pref.fxml"));
        try {
            quizPref = new QuizPref(listView, category, db, userName, list);
            v.setControllerFactory(c -> quizPref);
            modalPane.show(v.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private LinkedHashMap<String, String[]> convertToArray(String str) {
        var list = new LinkedHashMap<String, String[]>();
        Pattern pattern = Pattern.compile("\\[(.*?)]");
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            String group = matcher.group(1);
            String[] elements = Arrays.toString(group.split(",")).
                    replace("[", "").
                    replace("]", "").split(",");
            list.put(elements[2].trim(), elements);
        }
        return list;
    }

    private LinkedHashMap<String, String[]> getQuizzes(String userName) {
        LinkedHashMap<String, String[]> list = new LinkedHashMap<>();
        String sql = "SELECT quizzes FROM user WHERE user_name = ?";

        try (PreparedStatement pst = db.getConnection().prepareStatement(sql)) {

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


}
