package com.wuniszewski.currencyaccount.recruitment_task.app.service;

import com.wuniszewski.currencyaccount.recruitment_task.app.model.Account;
import com.wuniszewski.currencyaccount.recruitment_task.app.model.AccountHolder;
import com.wuniszewski.currencyaccount.recruitment_task.app.model.Currency;
import com.wuniszewski.currencyaccount.recruitment_task.app.repository.AccountRepository;
import com.wuniszewski.currencyaccount.recruitment_task.integration.nbp.NBPIntegrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountManagementService {

    private final AccountHolderService accountHolderService;

    private final AccountRepository accountRepository;

    private final NBPIntegrationService NBPIntegrationService;

    @Autowired
    public AccountManagementService(AccountHolderService accountHolderService, AccountRepository accountRepository) {
        this.accountHolderService = accountHolderService;
        this.accountRepository = accountRepository;
    }

    public ResponseEntity create(String firstName, String lastName, String PESEL_number, BigDecimal initialBalance) {
        AccountHolder accountHolder = accountHolderService.createAccountHolder(firstName, lastName, PESEL_number);
        Account newAccount = new Account(accountHolder, initialBalance);
        accountRepository.save(newAccount);
        return new ResponseEntity<>("Account has been created", HttpStatus.CREATED);
    }

    public ResponseEntity<Account> exchangeByPreviousCurrency(String PESEL_number, BigDecimal amountInPreviousCurrency, Currency targetCurrency) {

        return null;
    }

    public ResponseEntity<Account> exchangeByTargetCurrency(String PESEL_number, BigDecimal amountInTargetCurrency, Currency targetCurrency) {
        return null;
    }
}
