package com.setronica.eventing.exceptions;

public class TicketOrderException extends RuntimeException {
    public TicketOrderException(String message) {
        super(message);
    }

    public TicketOrderException(String message, Throwable cause) {
        super(message, cause);
    }
}