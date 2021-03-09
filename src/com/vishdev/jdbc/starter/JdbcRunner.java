package com.vishdev.jdbc.starter;

import com.vishdev.jdbc.starter.util.ConnectionManager;
import org.postgresql.Driver;


import java.sql.SQLException;
import java.sql.Statement;

public class JdbcRunner {

    public static final String INSERT_EXAMPLE = """
               INSERT INTO info (data)
               VALUES
               ('TEST1'),
               ('TEST2'),
               ('TEST3'),
               ('TEST1');
            """;
    public static final String UPDATE_EXAMPLE = """
               UPDATE info 
               SET data = 'testTest'
               WHERE id = 3;
            """;

    public static void main(String[] args) {
        Class<Driver> driverClass = Driver.class;
        try (var connection = ConnectionManager.open();
             var statement = connection.createStatement()) {
            int executeResult = statement.executeUpdate(UPDATE_EXAMPLE);
            System.out.println(executeResult);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
