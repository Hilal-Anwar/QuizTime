module org.helal_anwar.quiz_time.app {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;
    requires atlantafx.base;
    requires org.json;
    requires org.apache.commons.text;
    requires java.desktop;
    requires org.kordamp.ikonli.materialdesign2;
    requires java.sql;
    requires javafx.graphics;
    requires javafx.base;



    opens org.helal_anwar.quiz_time.app to javafx.fxml;
    exports org.helal_anwar.quiz_time.app;
    exports org.helal_anwar.quiz_time.app.question;
}