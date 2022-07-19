package com.wuniszewski.currencyaccount.recruitment_task.app.exception;

public class PESELNumberFormatInvalidException extends RuntimeException {

    public PESELNumberFormatInvalidException(String message) {
        super(message);
    }

    public PESELNumberFormatInvalidException(String message, Throwable cause) {
        super(message, cause);
    }
}
