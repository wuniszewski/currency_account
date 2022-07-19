package com.wuniszewski.currencyaccount.recruitment_task.app.controller;

import com.wuniszewski.currencyaccount.recruitment_task.app.exception.*;
import com.wuniszewski.currencyaccount.recruitment_task.integration.nbp.exception.NBPServiceUnavailableException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AccountHolderAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleAlreadyExistingAccountHolder(RuntimeException exception, WebRequest request) {
        final String exceptionMessage = exception.getMessage();
        return handleExceptionInternal(exception, exceptionMessage, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(UnderageAccountHolderCandidateException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    protected ResponseEntity<Object> handleUnderageAccountHolderCandidate(RuntimeException exception, WebRequest request) {
        final String exceptionMessage = exception.getMessage();
        return handleExceptionInternal(exception, exceptionMessage, new HttpHeaders(), HttpStatus.FORBIDDEN, request);
    }

    @ExceptionHandler(PESELNumberFormatInvalidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleInvalidPESELNumberFormat(RuntimeException exception, WebRequest request) {
        final String exceptionMessage = exception.getMessage();
        return handleExceptionInternal(exception, exceptionMessage, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(NBPServiceUnavailableException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    protected ResponseEntity<Object> handleUnavailableNBPApi(RuntimeException exception, WebRequest request) {
        final String exceptionMessage = exception.getMessage();
        return handleExceptionInternal(exception, exceptionMessage, new HttpHeaders(), HttpStatus.SERVICE_UNAVAILABLE, request);
    }

    @ExceptionHandler(AccountDoesNotExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleNonExistingAccount(RuntimeException exception, WebRequest request) {
        final String exceptionMessage = exception.getMessage();
        return handleExceptionInternal(exception, exceptionMessage, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(OperationNotAllowedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleNotAllowedOperation(RuntimeException exception, WebRequest request) {
        final String exceptionMessage = exception.getMessage();
        return handleExceptionInternal(exception, exceptionMessage, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
