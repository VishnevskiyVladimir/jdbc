package com.vishdev.jdbc.starter;

import com.vishdev.jdbc.starter.util.ConnectionManager;
import org.postgresql.Driver;


import java.sql.SQLException;

public class JdbcRunner {
    public static void main(String[] args) {
        Class<Driver> driverClass = Driver.class;
        try (var connection = ConnectionManager.open()) {
            System.out.println(connection.getTransactionIsolation());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
