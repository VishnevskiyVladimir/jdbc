package com.vishdev.jdbc.starter;

import com.vishdev.jdbc.starter.util.ConnectionManager;
import org.postgresql.Driver;


import java.sql.SQLException;
import java.sql.Statement;

public class JdbcRunner {
    public static void main(String[] args) {
        Class<Driver> driverClass = Driver.class;
        String sql = """
                CREATE TABLE IF NOT EXISTS info (
                id SERIAL PRIMARY KEY ,
                data TEXT NOT NULL 
                )     
                """;
        try (var connection = ConnectionManager.open();
             var statement = connection.createStatement()) {
            boolean executeResult = statement.execute(sql);
            System.out.println(executeResult);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
