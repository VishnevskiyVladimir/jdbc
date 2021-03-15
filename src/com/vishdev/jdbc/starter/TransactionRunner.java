package com.vishdev.jdbc.starter;

import com.vishdev.jdbc.starter.util.ConnectionManager;
import org.intellij.lang.annotations.Language;


import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TransactionRunner {
    public static void main(String[] args) throws SQLException {

        long flightId = 8;
        @Language(Queries.SQL_DIALECT)
        var deleteFlightSql = "DELETE FROM flight WHERE id = " + flightId;
        @Language(Queries.SQL_DIALECT)
        var deleteTicketSql = "DELETE FROM ticket WHERE flight_id = " + flightId;
        Connection connection = null;
        Statement statement = null;

        try {
            connection = ConnectionManager.open();
            connection.setAutoCommit(false);
            statement = connection.createStatement();

            statement.addBatch(deleteTicketSql);
            statement.addBatch(deleteFlightSql);
            int[] ints = statement.executeBatch();
            connection.commit();

        } catch (Exception e) {
            if (connection != null) {
                connection.rollback();
                throw e;
            }
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (statement != null) {
                statement.close();
            }
        }
    }

}
