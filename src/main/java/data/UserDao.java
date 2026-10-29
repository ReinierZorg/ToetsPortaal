package data;

import domain.User;
import domain.Teacher;
import domain.Student;

import java.sql.*;
import java.util.ArrayList;

public class UserDao {

    private final Connection connection;

    public UserDao(Connection connection) {
        this.connection = connection;
    }

    public ArrayList<User> getUsers() throws SQLException {
        ArrayList<User> users = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM Users");

        while(rs.next()) {

            User user;

            if(rs.getString("role").equals("teacher")) {

                user = new Teacher(
                        rs.getInt("user_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getString("password"));

            } else {

                user = new Student(
                        rs.getInt("user_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getString("password"));

            }

            users.add(user);
        }

        return users;
    }

    public String validateLogin(String email, String password) {

        try {

            String sql =
                    "SELECT role FROM Users WHERE email = ? AND password = ?";

            PreparedStatement statement =
                    connection.prepareStatement(sql);

            statement.setString(1, email);
            statement.setString(2, password);

            ResultSet rs = statement.executeQuery();

            if(rs.next()) {
                return rs.getString("role");
            }

            return null;

        } catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    public User getUserByEmail(String email){

        try{

            String sql =
                    "SELECT * FROM Users WHERE email = ?";

            PreparedStatement statement =
                    connection.prepareStatement(sql);

            statement.setString(1,email);

            ResultSet rs = statement.executeQuery();

            if(rs.next()){

                String role = rs.getString("role");

                if(role.equals("teacher")) {

                    return new Teacher(
                            rs.getInt("user_id"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getString("email"),
                            rs.getString("password")
                    );

                } else {

                    return new Student(
                            rs.getInt("user_id"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getString("email"),
                            rs.getString("password")
                    );

                }
            }

            return null;

        }catch(SQLException e){
            throw new RuntimeException(e);
        }

    }

}