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
            var executeResult = statement.executeQuery(Queries.SELECT_EXAMPLE);

            while (executeResult.next()) {
                System.out.print(executeResult.getLong("id") + "  ");
                System.out.println(executeResult.getString("data"));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
