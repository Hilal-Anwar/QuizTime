package org.helal_anwar.quiz_time.app;

public class UserScore {
    private  String name;
    private  int score;
    private  String duration; // Format: HH:mm:ss

    public UserScore(String name, int score, String duration) {
        this.name = name;
        this.score = score;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public String getDuration() {
        return duration;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
