package domain;

import java.util.ArrayList;
import java.util.List;

public class Question {

    private int id;
    private String text;
    private int points;

    private SchoolTest schoolTest;

    private List<AnswerOption> answerOptions;

    public Question(){

    }

    public Question(int id, String text, int points) {

        this.id = id;
        this.text = text;
        this.points = points;

        answerOptions = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public int getPoints() {
        return points;
    }

    public SchoolTest getSchoolTest() {
        return schoolTest;
    }

    public void setAnswers(List<AnswerOption> answers){
        this.answerOptions = answers;
    }

    public List<AnswerOption> getAnswerOptions() {
        return answerOptions;
    }

    public void setText(String text){
        this.text = text;
    }


    public void setPoints(int points){
        this.points = points;
    }

    public void setAnswerOptions(List<AnswerOption> answerOptions){
        this.answerOptions = answerOptions;
    }
}