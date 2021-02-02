package com.vishdev.jdbc.starter;

import com.vishdev.jdbc.starter.util.ConnectionManager;
import org.postgresql.Driver;


import java.sql.SQLException;
import java.sql.Statement;

public class JdbcRunner {
    public static void main(String[] args) {
        Class<Driver> driverClass = Driver.class;
        //CREATE SCHEMA my;
        String sql = """
                  
                CREATE TABLE book (
                id SERIAL PRIMARY KEY
               )   ;         
                """;
        try (var connection = ConnectionManager.open();
             var statement = connection.createStatement()) {
            System.out.println(connection.getTransactionIsolation());
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
