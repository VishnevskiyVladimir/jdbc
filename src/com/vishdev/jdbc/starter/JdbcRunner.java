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
//        Long flightId = 2L;
//        System.out.println(getTicketsByFlightId(flightId));
//        System.out.println(getFlightsBetween(LocalDate.of(2020,10,1).atStartOfDay(), LocalDateTime.now()));
        checkMetadata();
    }

    private static void checkMetadata() throws SQLException {
        try (Connection connection = ConnectionManager.get()) {
            DatabaseMetaData metaData = connection.getMetaData();
            var catalogs = metaData.getCatalogs();
            while (catalogs.next()) {
                var catalog = catalogs.getString("TABLE_CAT");
                System.out.println("Found database " + catalog);
                var schemas = metaData.getSchemas();
                System.out.println(" It contains following schemas:");
                while (schemas.next()) {
                    var schema = schemas.getString("TABLE_SCHEM");
                    System.out.println("    * " + schema);
                    var tables = metaData.getTables(catalog, schema, "%", new String[]{"TABLE"});
                    if (schema.equals("public")) {
                        System.out.println("        with following tables:");
                        while (tables.next()) {
                            var table = tables.getString("TABLE_NAME");
                            System.out.println("            - " + table);
                            var columns = metaData.getColumns(catalog, schema, table, "%");
                            while (columns.next()) {
                                System.out.println("                column name: " + columns.getString("COLUMN_NAME") +
                                        " data type from java.sql.Types: " + columns.getString("DATA_TYPE"));
                            }
                        }
                    }
                }
            }
        }
    }

    private static List<Long> getFlightsBetween(LocalDateTime start, LocalDateTime end) throws SQLException {
        @Language(Queries.SQL_DIALECT)
        String sql = """
                SELECT id
                FROM flight
                WHERE departure_date BETWEEN ? AND ?
                """;
        ArrayList<Long> flights = new ArrayList<>();

        try (Connection connection = ConnectionManager.get();
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
        try (Connection connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setFetchSize(50);
            preparedStatement.setQueryTimeout(10);
            preparedStatement.setMaxRows(100);

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
