package domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void createStudent() {

        Student student = new Student(
                1,
                "Jan",
                "Jansen",
                "jan@test.nl",
                "1234"
        );

        assertEquals(1, student.getUser_id());
    }

    @Test
    void createTeacher() {

        Teacher teacher = new Teacher(
                2,
                "Piet",
                "Pieters",
                "piet@test.nl",
                "abcd"
        );

        assertEquals(2, teacher.getUser_id());
    }
}