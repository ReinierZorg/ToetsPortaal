package domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SchoolTestTest {

    @Test
    void createTest() {

        SchoolTest test = new SchoolTest(
                1,
                "Java Toets",
                "Week 1"
        );

        assertNotNull(test);
    }

}