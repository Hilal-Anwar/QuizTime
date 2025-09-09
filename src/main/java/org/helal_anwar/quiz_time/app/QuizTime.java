package org.helal_anwar.quiz_time.app;

import atlantafx.base.theme.PrimerLight;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class QuizTime extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(QuizTime.class.getResource("splash_screen.fxml"));
        fxmlLoader.setControllerFactory(_->new SplashScreen(stage));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Quiz Time");
        stage.initStyle(StageStyle.TRANSPARENT);
        Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());
        stage.getIcons().add(new Image(QuizResourceLoader.loadStream("logo.png")));
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}
