package org.helal_anwar.quiz_time.app;

import atlantafx.base.controls.PasswordTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NewUser implements Initializable {
    ExistingUser existingUser;

    @FXML
    private PasswordTextField con_pass;

    @FXML
    private PasswordTextField pass;

    @FXML
    private TextField userId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public NewUser(ExistingUser existingUser) {
        this.existingUser = existingUser;
    }

    @FXML
    private void cancel() {
        existingUser.emptyLogin.empty_login.getChildren().removeLast();
    }

    @FXML
    private void create_new_account() {
        existingUser.emptyLogin.splashScreen.db.insertUser(userId.getText(), pass.getPassword(), "0");
        createQuizDashboard();
    }

    private void createQuizDashboard() {
        FXMLLoader fxmlLoader = new FXMLLoader(QuizResourceLoader.loadURL("dashboard.fxml"));
        fxmlLoader.setControllerFactory(_ -> new QuizDashboard(existingUser.emptyLogin.splashScreen.categoryMap,existingUser.emptyLogin.splashScreen.db,userId.getText(), existingUser.emptyLogin.splashScreen.defaultQuestions));
        try {
            existingUser.emptyLogin.splashScreen.stage.setScene(new Scene(fxmlLoader.load()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
