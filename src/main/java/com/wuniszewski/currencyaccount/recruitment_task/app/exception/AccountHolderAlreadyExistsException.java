package com.wuniszewski.currencyaccount.recruitment_task.app.exception;

public class AccountHolderAlreadyExistsException extends RuntimeException {

    public AccountHolderAlreadyExistsException(String message) {
        super(message);
    }
}
