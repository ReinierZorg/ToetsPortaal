package domain;

import java.util.ArrayList;
import java.util.List;

public class Student extends User {

    private List<Teacher> teachers;
    private List<SchoolTest> schoolTests;

    public Student(int id, String firstName, String lastName, String email, String password) {
        super(id, firstName, lastName, email, password, "student");
        teachers = new ArrayList<>();
        schoolTests = new ArrayList<>();
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }
    public List<SchoolTest> getTests() {
        return schoolTests;
    }
}
