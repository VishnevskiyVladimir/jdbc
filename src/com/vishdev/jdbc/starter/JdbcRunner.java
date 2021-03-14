package com.vishdev.jdbc.starter;

import com.vishdev.jdbc.starter.util.ConnectionManager;
import org.intellij.lang.annotations.Language;
import org.postgresql.Driver;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JdbcRunner {

    public static void main(String[] args) throws SQLException {
        String flightId = "2"; // CORRECT CODE
//        String flightId = "2 OR 1 = 1"; // SQL INJECTION RETURNING ALL TICKETS
//        String flightId = "2 OR 1 = 1; DROP TABLE info;"; // SQL INJECTION DROPPING TABLE
        System.out.println(getTicketIdsByFlightId(flightId));

    }

    public static List<Long> getTicketIdsByFlightId(String flightId) throws SQLException {
        @Language(Queries.SQL_DIALECT)
        String sql = """
                SELECT id
                FROM ticket
                WHERE flight_id = %s
                """.formatted(flightId);

        ArrayList<Long> tickets = new ArrayList<>();
        try (Connection connection = ConnectionManager.open();
        Statement statement = connection.createStatement()) {
            var resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                //tickets.add(resultSet.getLong("id"));
                tickets.add(resultSet.getObject("id", Long.class)); //NULL SAFE
            }
            return tickets;
        }
    }
}
