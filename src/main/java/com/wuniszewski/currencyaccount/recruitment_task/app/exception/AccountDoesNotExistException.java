package com.wuniszewski.currencyaccount.recruitment_task.app.exception;

public class AccountDoesNotExistException extends RuntimeException {

    public AccountDoesNotExistException(String message) {
        super(message);
    }
}
