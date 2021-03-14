package com.vishdev.jdbc.starter;

import com.vishdev.jdbc.starter.util.ConnectionManager;
import org.postgresql.Driver;


import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcRunner {

    public static void main(String[] args) {
        Class<Driver> driverClass = Driver.class;
        try (var connection = ConnectionManager.open();
             var statement = connection.createStatement()
        ) {
//            var executeResult = statement.executeUpdate(Queries.INSERT_1_RECORD_EXAMPLE,statement.RETURN_GENERATED_KEYS);
            var executeResult = statement.executeUpdate(Queries.INSERT_EXAMPLE,statement.RETURN_GENERATED_KEYS);
            ResultSet generatedKeys = statement.getGeneratedKeys();
            while (generatedKeys.next()){
                System.out.println(generatedKeys.getLong("id"));
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
