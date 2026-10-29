package services;

import domain.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    @Test
    void getUsers() {

        UserService service = new UserService();

        ArrayList<User> users = service.getUsers();

        assertFalse(users.isEmpty());
    }

}