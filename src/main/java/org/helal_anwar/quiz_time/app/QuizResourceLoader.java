package org.helal_anwar.quiz_time.app;

import java.io.InputStream;
import java.net.URL;

public class QuizResourceLoader {
    public static URL loadURL(String path) {
        return QuizResourceLoader.class.getResource(path);
    }

    public static String load(String path) {
        return loadURL(path).toExternalForm();
    }

    public static InputStream loadStream(String name) {
        return QuizResourceLoader.class.getResourceAsStream(name);
    }
}
