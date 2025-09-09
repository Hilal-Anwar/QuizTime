package org.helal_anwar.quiz_time.app;

import atlantafx.base.controls.PasswordTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ExistingUser implements Initializable {

    @FXML
    private PasswordTextField password;

    @FXML
    private TextField userId;
    EmptyLogin emptyLogin;

    public ExistingUser(EmptyLogin emptyLogin) {
        this.emptyLogin = emptyLogin;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @FXML
    private void log_in() {
        if (emptyLogin.splashScreen.db.isValidUser(userId.getText(), password.getPassword())) {
            FXMLLoader fxmlLoader = new FXMLLoader(QuizResourceLoader.loadURL("dashboard.fxml"));
            fxmlLoader.setControllerFactory(_ -> new QuizDashboard(emptyLogin.splashScreen.categoryMap,emptyLogin.splashScreen.db,userId.getText(),emptyLogin.splashScreen.defaultQuestions));
            try {
                emptyLogin.splashScreen.stage.setScene(new Scene(fxmlLoader.load()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            showAlert();
        }

    }

    @FXML
    private void create_new_account() {
        try {
            var v = new FXMLLoader(QuizResourceLoader.loadURL("new_login.fxml"));
            v.setControllerFactory(c -> new NewUser(this));
            StackPane stackPane = v.load();
            StackPane.setAlignment(stackPane, Pos.CENTER);
            emptyLogin.empty_login.getChildren().add(stackPane);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void showAlert() {
        var alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("No user found");
        alert.setHeaderText("There is no such user or user_name/password is incorrect.");
        alert.setContentText("Please create a new account.");
        alert.showAndWait();
    }
}
