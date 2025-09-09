package org.helal_anwar.quiz_time.app;


import java.text.MessageFormat;
import java.util.Scanner;

public class SqlFileReader {
    private String query = "";

    public SqlFileReader(String filePath) {
        Scanner scanner = new Scanner(QuizResourceLoader.loadStream(filePath));
        while (scanner.hasNextLine()) query = MessageFormat.format("{0}{1}\n", query, scanner.nextLine());
    }

    public String getQuery() {
        return query;
    }
}
