package domain;

import java.util.ArrayList;
import java.util.List;

public class SchoolTest {

    private int id;
    private String title;
    private String description;

    private Teacher teacher;
    private List<Question> questions;

    public SchoolTest() {

    }

    public SchoolTest(int id, String title, String description) {

        this.id = id;
        this.title = title;
        this.description = description;

        questions = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setQuestions(List<Question> questions){
        this.questions = questions;
    }
}