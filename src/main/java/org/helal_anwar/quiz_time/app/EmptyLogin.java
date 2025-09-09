package org.helal_anwar.quiz_time.app;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EmptyLogin implements Initializable {
    @FXML
    public StackPane empty_login;
    SplashScreen splashScreen;
    public EmptyLogin(SplashScreen splashScreen) {
         this.splashScreen = splashScreen;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            var v=new FXMLLoader(QuizResourceLoader.loadURL("old_login.fxml"));
            v.setControllerFactory(c->new ExistingUser(this));
            StackPane stackPane = v.load();
            StackPane.setAlignment(stackPane, Pos.CENTER);
            empty_login.getChildren().add(stackPane);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
