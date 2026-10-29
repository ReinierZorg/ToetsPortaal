package domain;

public class AnswerOption {

    private int id;
    private String text;
    private boolean correct;

    private Question question;

    public AnswerOption(){

    }

    public AnswerOption(int id, String text, boolean correct) {

        this.id = id;
        this.text = text;
        this.correct = correct;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public boolean isCorrect() {
        return correct;
    }

    public Question getQuestion() {
        return question;
    }

    public void setText(String text){
        this.text = text;
    }

    public void setCorrect(boolean correct){
        this.correct = correct;
    }
}