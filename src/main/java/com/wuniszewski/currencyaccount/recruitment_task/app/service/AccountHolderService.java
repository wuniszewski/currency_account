package com.wuniszewski.currencyaccount.recruitment_task.app.service;

import com.wuniszewski.currencyaccount.recruitment_task.app.exception.AccountHolderAlreadyExistsException;
import com.wuniszewski.currencyaccount.recruitment_task.app.exception.PESELNumberFormatInvalidException;
import com.wuniszewski.currencyaccount.recruitment_task.app.exception.UnderageAccountHolderCandidateException;
import com.wuniszewski.currencyaccount.recruitment_task.app.model.AccountHolder;
import com.wuniszewski.currencyaccount.recruitment_task.app.repository.AccountHolderRepository;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

import static org.jboss.logging.Logger.Level.DEBUG;

@Service
public class AccountHolderService {

    private final Logger LOGGER = Logger.getLogger(AccountHolderService.class.getName());

    private final int PESEL_NUMBER_LENGTH = 11;

    private final int LEGAL_AGE_LIMIT = 18;

    private final int MONTH_DIGITS_ADDITION_IN_PESEL_WHEN_BORN_AFTER_2000s = 20;

    private final AccountHolderRepository accountHolderRepository;

    @Autowired
    public AccountHolderService(AccountHolderRepository accountHolderRepository) {
        this.accountHolderRepository = accountHolderRepository;
    }

    public AccountHolder create(final String firstName, final String lastName, final String PESELNumber) {
        validatePESELNumber(PESELNumber);
        validateIfCandidateIsUnderage(PESELNumber);
        validateIfAccountAlreadyExists(PESELNumber);
        AccountHolder createdAccountHolder = accountHolderRepository.save(new AccountHolder(firstName, lastName, PESELNumber));
        LOGGER.log(DEBUG, String.format("Account with PESEL number: '%s' has been created.", createdAccountHolder.getPESELNumber()));
        return createdAccountHolder;
    }

    public void delete(AccountHolder accountHolder) {
        accountHolderRepository.delete(accountHolder);
        LOGGER.log(DEBUG, String.format("Account with PESEL number: '%s' has been deleted.", accountHolder.getPESELNumber()));
    }

    private void validatePESELNumber(String PESELNumber) {
        int charCount = PESELNumber.trim().length();
        if (charCount != PESEL_NUMBER_LENGTH) {
            throw new PESELNumberFormatInvalidException("Wrong number of characters in a PESEL number.");
        }
        try {
            Long.valueOf(PESELNumber);
        } catch (NumberFormatException e) {
            throw new PESELNumberFormatInvalidException("PESEL number is not a number. It must contain only digits.", e);
        }
    }

    private void validateIfCandidateIsUnderage(String PESELNumber) {
        int yearOfBirth = extractCorrectYear(PESELNumber);
        int monthOfBirth = extractCorrectMonth(PESELNumber);
        int dayOfBirth = Integer.parseInt(PESELNumber.substring(4, 6));
        LocalDate dateOfBirth = LocalDate.of(yearOfBirth, monthOfBirth, dayOfBirth);
        Period currentAge = Period.between(dateOfBirth, LocalDate.now());
        if (currentAge.getYears() < LEGAL_AGE_LIMIT) {
            throw new UnderageAccountHolderCandidateException("Person is to young to hold an account.");
        }
    }

    private void validateIfAccountAlreadyExists(String PESELNumber) {
        Optional<AccountHolder> accountHolder = accountHolderRepository.getAccountHolderByPESELNumber(PESELNumber);
        if (accountHolder.isPresent()) {
            throw new AccountHolderAlreadyExistsException("Account holder already exists.");
        }
    }

    private int extractCorrectYear(String PESELNumber) {
        String yearDigitsFromPESEL = PESELNumber.substring(0, 2);
        String monthDigitsFromPESEL = PESELNumber.substring(2, 4);
        int month = Integer.parseInt(monthDigitsFromPESEL);
        if (month > MONTH_DIGITS_ADDITION_IN_PESEL_WHEN_BORN_AFTER_2000s) {
            return Integer.parseInt("20" + yearDigitsFromPESEL);
        }
        return Integer.parseInt("19" + yearDigitsFromPESEL);
    }

    private int extractCorrectMonth(String PESELNumber) {
        int monthDigits = Integer.parseInt(PESELNumber.substring(2, 4));
        if (monthDigits > MONTH_DIGITS_ADDITION_IN_PESEL_WHEN_BORN_AFTER_2000s) {
            return monthDigits - 20;
        }
        return monthDigits;
    }
}
