package services;

import data.TestDao;
import domain.AssignedTest;
import domain.SchoolTest;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TestService {

    public ArrayList<SchoolTest> getTests() {

        try(Connection connection = GetConnection.getConnection()) {

            TestDao testDao = new TestDao(connection);

            return testDao.getTests();

        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createTest(
            SchoolTest schoolTest,
            String email
    ){

        try(Connection connection = GetConnection.getConnection()){

            TestDao dao = new TestDao(connection);

            dao.createTest(schoolTest, email);

            connection.commit();
            System.out.println("Commit uitgevoerd!");

        } catch(SQLException e){

            e.printStackTrace();

            throw new RuntimeException(e);
        }
    }
    public List<AssignedTest> getAssignedTests(String email) {

        try(Connection connection = GetConnection.getConnection()) {

            TestDao dao = new TestDao(connection);

            return dao.getAssignedTests(email);

        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public SchoolTest getAssignedTestForStudent(
            int assignedTestId, String email) {

        try (Connection connection = GetConnection.getConnection()) {
            TestDao dao = new TestDao(connection);

            return dao.getAssignedTestForStudent(
                    assignedTestId, email
            );
        } catch (SQLException e) {
            throw new RuntimeException(
                    "Fout bij het ophalen van de toegewezen toets", e
            );
        }
    }


}