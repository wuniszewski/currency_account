package com.wuniszewski.currencyaccount.recruitment_task.app.exception;

public class OperationNotAllowedException extends RuntimeException {

    public OperationNotAllowedException(String message) {
        super(message);
    }
}
