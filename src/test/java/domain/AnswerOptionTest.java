package domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnswerOptionTest {

    @Test
    void createAnswerOption() {

        AnswerOption option = new AnswerOption(
                1,
                "Een programmeertaal",
                true
        );

        assertNotNull(option);
    }

}