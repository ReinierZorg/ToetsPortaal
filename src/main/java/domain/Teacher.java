package domain;

import java.util.ArrayList;
import java.util.List;

public class Teacher extends User {

    private List<Student> students;
    private List<SchoolTest> schoolTests;

    public Teacher(int id, String firstName, String lastName,
                   String email, String password) {

        super(id, firstName, lastName, email, password, "teacher");

        students = new ArrayList<>();
        schoolTests = new ArrayList<>();
    }

    public List<Student> getStudents() {
        return students;
    }

    public List<SchoolTest> getTests() {
        return schoolTests;
    }
}