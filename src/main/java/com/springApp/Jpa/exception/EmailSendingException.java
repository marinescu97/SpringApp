package com.springApp.Jpa.exception;

public class EmailSendingException extends Exception{
    public EmailSendingException(String message) {
        super(message);
    }
}
