package org.helal_anwar.quiz_time.app;

import atlantafx.base.controls.ModalPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class Navigation implements Initializable {
    @FXML
    Label userName;
    String name;
    @FXML
    ImageView closeButton;
    ModalPane modalPane;

    public Navigation(String name, ModalPane modalPane) {
        this.name = name;
        this.modalPane = modalPane;
        System.out.println(name);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userName.setText("Hi! " + getFirstName(name));
    }

    private String getFirstName(String text) {
        if (text.contains(" ")) {
            String ab = text.split(" ")[0];
            return Character.toUpperCase(ab.charAt(0)) + ab.substring(1);
        }
        return Character.toUpperCase(text.charAt(0)) + text.substring(1);
    }
    @FXML
    public void onClose(MouseEvent event) {
        System.out.println("Close button pressed");
        modalPane.hide();
    }
}
