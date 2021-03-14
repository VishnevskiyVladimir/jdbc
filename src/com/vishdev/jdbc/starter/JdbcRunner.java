package com.vishdev.jdbc.starter;

import com.vishdev.jdbc.starter.util.ConnectionManager;
import org.intellij.lang.annotations.Language;
import org.postgresql.Driver;


import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.time.LocalTime.now;

public class JdbcRunner {

    public static void main(String[] args) throws SQLException {
        Long flightId = 2L;
//        System.out.println(getTicketsByFlightId(flightId));
        System.out.println(getFlightsBetween(LocalDate.of(2020,10,1).atStartOfDay(), LocalDateTime.now()));

    }

    private static List<Long> getFlightsBetween(LocalDateTime start, LocalDateTime end) throws SQLException {
        @Language(Queries.SQL_DIALECT)
        String sql = """
                SELECT id
                FROM flight
                WHERE departure_date BETWEEN ? AND ?
                """;
        ArrayList<Long> flights = new ArrayList<>();

        try (Connection connection = ConnectionManager.open();
             var preparedStatement = connection.prepareStatement(sql)) {
            System.out.println(preparedStatement);
            preparedStatement.setTimestamp(1, Timestamp.valueOf(start));
            System.out.println(preparedStatement);
            preparedStatement.setTimestamp(2, Timestamp.valueOf(end));
            System.out.println(preparedStatement);
            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                //tickets.add(resultSet.getLong("id"));
                flights.add(resultSet.getObject("id", Long.class)); //NULL SAFE
            }
            return flights;
        }
    }

    private static List<Long> getTicketsByFlightId(Long flightId) throws SQLException {
        @Language(Queries.SQL_DIALECT)
        String sql = """
                SELECT id
                FROM ticket
                WHERE flight_id = ?
                """;

        ArrayList<Long> tickets = new ArrayList<>();
        try (Connection connection = ConnectionManager.open();
             var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, flightId);
            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                //tickets.add(resultSet.getLong("id"));
                tickets.add(resultSet.getObject("id", Long.class)); //NULL SAFE
            }
            return tickets;
        }
    }
}
