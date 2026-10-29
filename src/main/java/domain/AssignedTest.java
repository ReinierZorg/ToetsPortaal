package domain;

public class AssignedTest {

    private int assignedTestId;
    private int score;
    private String status;

    private SchoolTest test;

    public AssignedTest(int assignedTestId, String status, int score, SchoolTest test) {
        this.assignedTestId = assignedTestId;
        this.status = status;
        this.score = score;
        this.test = test;
    }

    public int getAssignedTestId() {
        return assignedTestId;
    }

    public String getStatus() {
        return status;
    }

    public int getScore() {
        return score;
    }

    public SchoolTest getTest() {
        return test;
    }
}