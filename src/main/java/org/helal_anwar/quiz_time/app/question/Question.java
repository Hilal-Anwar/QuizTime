package org.helal_anwar.quiz_time.app.question;
import java.util.List;

public class Question {
    private String question;
    private List<String> correctAnswers;
    private List<String> options;
    private int num;
    public Question(String question, List<String> correctAnswers, List<String> options,int num) {
        this.question = question;
        this.correctAnswers = correctAnswers;
        this.options = options;
        this.num = num;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getCorrectAnswers() {
        return correctAnswers;
    }

    public List<String> getOptions() {
        return options;
    }

    public boolean isMultipleAnswer() {
        return correctAnswers.size() > 1;
    }

    public int getNum() {
        return num;
    }
}