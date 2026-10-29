package domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuestionTest {

    @Test
    void createQuestion() {

        Question question = new Question(
                1,
                "Wat is Java?",
                5
        );

        assertNotNull(question);
    }

}