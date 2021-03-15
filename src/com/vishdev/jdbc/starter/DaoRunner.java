package com.vishdev.jdbc.starter;


import com.vishdev.jdbc.starter.dao.TicketDao;
import com.vishdev.jdbc.starter.entity.Ticket;

import java.math.BigDecimal;

public class DaoRunner {

    public static void main(String[] args) {
//        deleteTest(57L);
        saveTest();
    }

    private static void deleteTest(Long id) {
        var ticketDao = TicketDao.getInstance();
        var deleteResult = ticketDao.delete(id);
        System.out.println(deleteResult);
    }

    private static void saveTest() {
        var ticketDao = TicketDao.getInstance();
        var ticket = new Ticket();
        ticket.setPassengerNo("1234567");
        ticket.setPassengerName("Test");
        ticket.setFlightId(3L);
        ticket.setSeatNo("B3");
        ticket.setCost(BigDecimal.TEN);

        var savedTicket = ticketDao.save(ticket);
        System.out.println(savedTicket);
    }
}