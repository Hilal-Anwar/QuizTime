package org.helal_anwar.quiz_time.app.question;
/*
https://opentdb.com/api.php?amount=5&category=9&difficulty=medium&type=multiple
 */

import org.apache.commons.text.StringEscapeUtils;
import org.json.*;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

public class QuizService {

    public static List<Question> fetchQuestions(String category, String level, String type, String no_of_questions) throws Exception {
        List<Question> questions = new ArrayList<>();
        JSONArray results = getObjects("https://opentdb.com/api.php?amount=%s&category=%s&difficulty=%s&type=%s".
                formatted(no_of_questions, category, level, type), "results");
        return getQuestions(questions, results);
    }

    public static List<Question> fetchDefaultQuestions() throws Exception {
        List<Question> questions = new ArrayList<>();
        JSONArray results = getObjects("https://opentdb.com/api.php?amount=10&type=multiple","results");
        return getQuestions(questions, results);
    }

    private static List<Question> getQuestions(List<Question> questions, JSONArray results) {
        for (int i = 0; i < results.length(); i++) {
            JSONObject q = results.getJSONObject(i);
            String questionText = StringEscapeUtils.unescapeHtml4(q.getString("question"));
            String correct = StringEscapeUtils.unescapeHtml4(q.getString("correct_answer"));
            JSONArray incorrect = q.getJSONArray("incorrect_answers");

            List<String> options = new ArrayList<>();
            for (int j = 0; j < incorrect.length(); j++) {
                options.add(StringEscapeUtils.unescapeHtml4(incorrect.getString(j)));
            }

            List<String> correctAnswers = new ArrayList<>();
            correctAnswers.add(correct);

            options.addAll(correctAnswers);
            Collections.shuffle(options);

            questions.add(new Question(questionText, correctAnswers, options, i));
        }

        return questions;
    }

    public static LinkedHashMap<String, Long> fetchCategory() throws URISyntaxException, IOException {
        JSONArray category = getObjects("https://opentdb.com/api_category.php", "trivia_categories");
        LinkedHashMap<String, Long> categoryMap = new LinkedHashMap<>();
        for (int i = 0; i < category.length(); i++) {
            JSONObject q = category.getJSONObject(i);
            Long id = Long.parseLong(Objects.requireNonNull(StringEscapeUtils.unescapeHtml4(String.valueOf(q.getInt("id")))));
            String name = StringEscapeUtils.unescapeHtml4(q.getString("name"));
            categoryMap.put(name, id);
        }
        return categoryMap;
    }

    private static JSONArray getObjects(String urlLink, String key) throws URISyntaxException, IOException {
        URL url = new URI(urlLink).toURL();
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        Scanner scanner = new Scanner(new InputStreamReader(conn.getInputStream()));
        StringBuilder json = new StringBuilder();
        while (scanner.hasNext()) {
            json.append(scanner.nextLine());
        }
        scanner.close();

        JSONObject obj = new JSONObject(json.toString());
        return switch (key) {
            case "results" -> obj.getJSONArray("results");
            case "trivia_categories" -> obj.getJSONArray("trivia_categories");
            default -> throw new IllegalStateException("Unexpected value: " + key);
        };
    }
}