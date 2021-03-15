package com.vishdev.jdbc.starter;

import com.vishdev.jdbc.starter.util.ConnectionManager;
import org.intellij.lang.annotations.Language;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class TransactionRunner {
    public static void main(String[] args) throws SQLException {
        @Language(Queries.SQL_DIALECT)
        String deleteFlightSql = """
                DELETE FROM flight
                WHERE id = ?;
                """;
        @Language(Queries.SQL_DIALECT)
        String deleteTicketSql = """
                DELETE FROM ticket
                WHERE flight_id = ?;
                """;
        long flightId = 8;
        Connection connection = null;
        PreparedStatement deleteFlightStatement = null;
        PreparedStatement deleteTicketStatement = null;
        try  {
            connection = ConnectionManager.open();
            deleteFlightStatement = connection.prepareStatement(deleteFlightSql);
            deleteTicketStatement = connection.prepareStatement(deleteTicketSql);
            connection.setAutoCommit(false);

            deleteFlightStatement.setLong(1, flightId);
            deleteTicketStatement.setLong(1, flightId);
            deleteTicketStatement.executeUpdate();
            if(true) {
                throw new RuntimeException("Oops");
            }
            deleteFlightStatement.executeUpdate();
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
            if(deleteFlightStatement != null) {
                deleteFlightStatement.close();
            }
            if (deleteTicketStatement != null) {
                deleteTicketStatement.close();
            }
        }
    }

}
