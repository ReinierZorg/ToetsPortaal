package data;

import domain.User;
import org.junit.jupiter.api.Test;
import services.GetConnection;

import java.sql.Connection;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {

    @Test
    void getUsers() throws Exception {

        Connection connection = GetConnection.getConnection();

        UserDao dao = new UserDao(connection);

        ArrayList<User> users = dao.getUsers();

        assertFalse(users.isEmpty());

        connection.close();
    }

}