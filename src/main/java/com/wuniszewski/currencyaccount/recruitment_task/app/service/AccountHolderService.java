package com.wuniszewski.currencyaccount.recruitment_task.app.service;

import com.wuniszewski.currencyaccount.recruitment_task.app.exception.AccountHolderAlreadyExistsException;
import com.wuniszewski.currencyaccount.recruitment_task.app.exception.PESELNumberFormatInvalidException;
import com.wuniszewski.currencyaccount.recruitment_task.app.exception.UnderageAccountHolderCandidateException;
import com.wuniszewski.currencyaccount.recruitment_task.app.model.AccountHolder;
import com.wuniszewski.currencyaccount.recruitment_task.app.repository.AccountHolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

@Service
public class AccountHolderService {

    private final int PESEL_NUMBER_LENGTH = 11;

    private final int LEGAL_AGE_LIMIT = 18;

    private final AccountHolderRepository accountHolderRepository;

    @Autowired
    public AccountHolderService(AccountHolderRepository accountHolderRepository) {
        this.accountHolderRepository = accountHolderRepository;
    }

    public AccountHolder createAccountHolder(final String firstName, final String lastName, final String PESEL_number) {
        validatePESELNumber(PESEL_number);
        validateIfCandidateIsUnderage(PESEL_number);
        validateIfAccountAlreadyExists(PESEL_number);
        return accountHolderRepository.save(new AccountHolder(firstName, lastName, PESEL_number));
    }

    private void validatePESELNumber(String PESEL_number) {
        int charCount = PESEL_number.trim().length();
        if (charCount != PESEL_NUMBER_LENGTH) {
            throw new PESELNumberFormatInvalidException("Wrong number of characters in a PESEL number.");
        }
        try {
            Long.valueOf(PESEL_number);
        } catch (NumberFormatException e) {
            throw new PESELNumberFormatInvalidException("PESEL number is not a valid number. It must contain only digits.", e);
        }
    }

    private void validateIfCandidateIsUnderage(String PESEL_number) {
        int yearOfBirth = extractCorrectYear(PESEL_number);
        int monthOfBirth = Integer.parseInt(PESEL_number.substring(2, 4));
        int dayOfBirth = Integer.parseInt(PESEL_number.substring(4, 6));
        LocalDate dateOfBirth = LocalDate.of(yearOfBirth, monthOfBirth, dayOfBirth);
        Period currentAge = Period.between(dateOfBirth, LocalDate.now());
        if (currentAge.getYears() < LEGAL_AGE_LIMIT) {
            throw new UnderageAccountHolderCandidateException("Person is to young to hold an account.");
        }
    }

    private void validateIfAccountAlreadyExists(String PESEL_number) {
        Optional<AccountHolder> accountHolder = accountHolderRepository.getAccountHolderByPESEL_number(PESEL_number);
        if (accountHolder.isPresent()) {
            throw new AccountHolderAlreadyExistsException("Account holder already exists.");
        }
    }

    private int extractCorrectYear(String PESEL_number) {
        String yearDigitsFromPESEL = PESEL_number.substring(0, 2);
        int lastTwoYearDigits = Integer.parseInt(yearDigitsFromPESEL);
        if (lastTwoYearDigits <= extractLastTwoYearDigitsFromCurrentYear()) {
            return Integer.parseInt("20" + yearDigitsFromPESEL);
        } else {
            return Integer.parseInt("19" + lastTwoYearDigits);
        }
    }

    private long extractLastTwoYearDigitsFromCurrentYear() {
        String currentYear = String.valueOf(LocalDate.now().getYear());
        return Long.parseLong(currentYear.substring(0, 2));
    }
}
