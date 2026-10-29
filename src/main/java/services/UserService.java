package services;

import data.TestDao;
import data.UserDao;
import domain.SchoolTest;
import domain.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserService {

    public String validateLogin(String email,String password){

        try(Connection connection = GetConnection.getConnection()){

            UserDao dao = new UserDao(connection);

            return dao.validateLogin(email,password);

        }catch(Exception e){
            throw new RuntimeException(e);
        }

    }

    public User getUserByEmail(String email){

        try(Connection connection = GetConnection.getConnection()){

            UserDao dao = new UserDao(connection);

            return dao.getUserByEmail(email);

        }catch(Exception e){
            throw new RuntimeException(e);
        }

    }

    public ArrayList<User> getUsers(){

        try(Connection connection = GetConnection.getConnection()){

            UserDao dao = new UserDao(connection);

            return dao.getUsers();

        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public List<SchoolTest> getTestsByTeacher(String email) {

        try(Connection connection = GetConnection.getConnection()) {

            UserDao userDao = new UserDao(connection);

            int teacherId = userDao.getUserByEmail(email).getUser_id();

            TestDao dao = new TestDao(connection);

            return dao.getTestsByTeacherId(teacherId);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}