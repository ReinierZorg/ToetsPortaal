package services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class GetConnection {

    public static Connection getConnection() throws SQLException {

        Properties properties = new Properties();

        properties.setProperty(
                "user",
                "reinier"
        );

        properties.setProperty(
                "password",
                "Wachtwoord123!"
        );


        Connection connection =
                DriverManager.getConnection(
                        "jdbc:postgresql://localhost:5432/ToetsPortaal",
                        properties
                );
        connection.setAutoCommit(false);

        return connection;
    }
}