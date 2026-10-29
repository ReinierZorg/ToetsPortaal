package data;

import domain.AnswerOption;
import domain.AssignedTest;
import domain.Question;
import domain.SchoolTest;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TestDao {

    private final Connection connection;

    public TestDao(Connection connection) {
        this.connection = connection;
    }

    public ArrayList<SchoolTest> getTests() throws SQLException {

        ArrayList<SchoolTest> schoolTests = new ArrayList<>();

        Statement statement = connection.createStatement();

        ResultSet rs = statement.executeQuery("SELECT * FROM Test");

        while(rs.next()) {

            schoolTests.add(new SchoolTest(
                    rs.getInt("test_id"),
                    rs.getString("title"),
                    rs.getString("description")
            ));

        }

        return schoolTests;
    }

    public List<SchoolTest> getTestsByTeacherId(int teacherId) throws SQLException {

        List<SchoolTest> tests = new ArrayList<>();

        PreparedStatement statement =
                connection.prepareStatement(
                        """
                        SELECT test_id,title,description
                        FROM test
                        WHERE teacher_id = ?
                        ORDER BY test_id
                        """);

        statement.setInt(1, teacherId);

        ResultSet rs = statement.executeQuery();

        while(rs.next()) {

            tests.add(
                    new SchoolTest(
                            rs.getInt("test_id"),
                            rs.getString("title"),
                            rs.getString("description")
                    )
            );
        }

        return tests;
    }

    public void createTest(
            SchoolTest schoolTest,
            String email
    ) throws SQLException {

        // teacher zoeken
        PreparedStatement teacherStatement =
                connection.prepareStatement(
                        """
                        SELECT user_id
                        FROM users
                        WHERE email = ?
                        """
                );

        teacherStatement.setString(1,email);

        ResultSet teacherResult =
                teacherStatement.executeQuery();

        if(!teacherResult.next()){
            throw new RuntimeException("Teacher niet gevonden");
        }

        int teacherId =
                teacherResult.getInt("user_id");

        // test toevoegen
        PreparedStatement testStatement =
                connection.prepareStatement(
                        """
                        INSERT INTO test(title,description,teacher_id)
                        VALUES(?,?,?)
                        RETURNING test_id
                        """
                );

        testStatement.setString(
                1,
                schoolTest.getTitle()
        );

        testStatement.setString(
                2,
                schoolTest.getDescription()
        );

        testStatement.setInt(
                3,
                teacherId
        );

        ResultSet testResult =
                testStatement.executeQuery();

        testResult.next();

        int testId =
                testResult.getInt("test_id");

        // vragen toevoegen
        for(Question question : schoolTest.getQuestions()){

            PreparedStatement questionStatement =
                    connection.prepareStatement(
                            """
                            INSERT INTO question(
                                question_text,
                                points,
                                test_id
                            )
                            VALUES(?,?,?)
                            RETURNING question_id
                            """
                    );

            questionStatement.setString(
                    1,
                    question.getText()
            );

            questionStatement.setInt(
                    2,
                    question.getPoints()
            );

            questionStatement.setInt(
                    3,
                    testId
            );

            ResultSet questionResult =
                    questionStatement.executeQuery();

            questionResult.next();

            int questionId =
                    questionResult.getInt("question_id");

            // antwoorden toevoegen
            for(AnswerOption answer :
                    question.getAnswerOptions()){

                PreparedStatement answerStatement =
                        connection.prepareStatement(
                                """
                                INSERT INTO answeroption(
                                    answer_text,
                                    is_correct,
                                    question_id
                                )
                                VALUES(?,?,?)
                                """
                        );

                answerStatement.setString(
                        1,
                        answer.getText()
                );

                answerStatement.setBoolean(
                        2,
                        answer.isCorrect()
                );

                answerStatement.setInt(
                        3,
                        questionId
                );
                answerStatement.executeUpdate();
            }
        }
    }
    public List<AssignedTest> getAssignedTests(String email) throws SQLException {

        List<AssignedTest> tests = new ArrayList<>();

        PreparedStatement studentStatement =
                connection.prepareStatement("""
                    SELECT user_id
                    FROM users
                    WHERE email = ?
                    """);

        studentStatement.setString(1, email);

        ResultSet studentResult = studentStatement.executeQuery();

        if (!studentResult.next()) {
            throw new RuntimeException("Student niet gevonden");
        }

        int studentId = studentResult.getInt("user_id");

        PreparedStatement statement =
                connection.prepareStatement("""
                    SELECT
                        a.assigned_test_id,
                        a.status,
                        a.score,
                        t.test_id,
                        t.title,
                        t.description
                    FROM assignedtest a
                    JOIN test t
                      ON a.test_id = t.test_id
                    WHERE a.student_id = ?
                    ORDER BY a.assigned_test_id
                    """);

        statement.setInt(1, studentId);

        ResultSet rs = statement.executeQuery();

        while (rs.next()) {

            SchoolTest test =
                    new SchoolTest(
                            rs.getInt("test_id"),
                            rs.getString("title"),
                            rs.getString("description")
                    );

            tests.add(
                    new AssignedTest(
                            rs.getInt("assigned_test_id"),
                            rs.getString("status"),
                            rs.getInt("score"),
                            test
                    )
            );
        }

        return tests;
    }
    public SchoolTest getAssignedTestForStudent(
            int assignedTestId, String email) throws SQLException {

        String sql =
                "SELECT t.test_id, t.title, t.description " +
                        "FROM assignedtest a " +
                        "JOIN test t ON t.test_id = a.test_id " +
                        "JOIN users u ON u.user_id = a.student_id " +
                        "WHERE a.assigned_test_id = ? " +
                        "AND u.email = ? AND u.role = 'student'";

        SchoolTest test;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, assignedTestId);
            ps.setString(2, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }

                test = new SchoolTest(
                        rs.getInt("test_id"),
                        rs.getString("title"),
                        rs.getString("description")
                );
            }
        }

        List<Question> questions = new ArrayList<>();

        String questionSql =
                "SELECT question_id, question_text, points " +
                        "FROM question WHERE test_id = ? " +
                        "ORDER BY question_id";

        try (PreparedStatement ps = connection.prepareStatement(questionSql)) {
            ps.setInt(1, test.getId());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Question question = new Question(
                            rs.getInt("question_id"),
                            rs.getString("question_text"),
                            rs.getInt("points")
                    );

                    questions.add(question);
                }
            }
        }

        // Haal de antwoordopties op, zonder is_correct naar de student te sturen.
        for (Question question : questions) {
            question.setAnswerOptions(
                    getStudentAnswerOptions(question.getId())
            );
        }

        test.setQuestions(questions);
        return test;
    }

    private List<AnswerOption> getStudentAnswerOptions(
            int questionId) throws SQLException {

        List<AnswerOption> options = new ArrayList<>();

        String sql =
                "SELECT answer_option_id, answer_text " +
                        "FROM answeroption " +
                        "WHERE question_id = ? " +
                        "ORDER BY answer_option_id";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, questionId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    options.add(new AnswerOption(
                            rs.getInt("answer_option_id"),
                            rs.getString("answer_text"),
                            false
                    ));
                }
            }
        }

        return options;
    }

}