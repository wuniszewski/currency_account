package com.wuniszewski.currencyaccount.recruitment_task.app.service;

import com.wuniszewski.currencyaccount.recruitment_task.app.converter.DTOConverter;
import com.wuniszewski.currencyaccount.recruitment_task.app.dto.AccountDTO;
import com.wuniszewski.currencyaccount.recruitment_task.app.exception.AccountDoesNotExistException;
import com.wuniszewski.currencyaccount.recruitment_task.app.exception.OperationNotAllowedException;
import com.wuniszewski.currencyaccount.recruitment_task.app.model.Account;
import com.wuniszewski.currencyaccount.recruitment_task.app.model.AccountHolder;
import com.wuniszewski.currencyaccount.recruitment_task.app.model.Currency;
import com.wuniszewski.currencyaccount.recruitment_task.app.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class AccountManagementService {

    private final AccountHolderService accountHolderService;

    private final AccountRepository accountRepository;

    private final DTOConverter<AccountDTO, Account> accountConverter;

    @Autowired
    public AccountManagementService(AccountHolderService accountHolderService, AccountRepository accountRepository,
                                    DTOConverter<AccountDTO, Account> accountConverter) {
        this.accountHolderService = accountHolderService;
        this.accountRepository = accountRepository;
        this.accountConverter = accountConverter;
    }

    public ResponseEntity create(String firstName, String lastName, String PESELNumber, BigDecimal initialBalance) {
        AccountHolder accountHolder = accountHolderService.create(firstName, lastName, PESELNumber);
        verifyInitialBalance(accountHolder, initialBalance);
        Account newAccount = new Account(accountHolder, initialBalance);
        accountRepository.save(newAccount);
        return new ResponseEntity<>("Account has been created", HttpStatus.CREATED);
    }

    public ResponseEntity<AccountDTO> exchangeCurrency(String PESELNumber,
                                                       BigDecimal amountInBaseCurrency,
                                                       Currency baseCurrency,
                                                       Currency targetCurrency,
                                                       ExchangeStrategy exchangeStrategy) {
        Optional<Account> accountOpt = accountRepository.getAccountByPESELNumber(PESELNumber);
        if (accountOpt.isEmpty()) {
            throw new AccountDoesNotExistException("Account with given PESEL number doesn't exist.");
        }
        Account account = accountOpt.get();
        exchangeStrategy.exchangeCurrencyInAccount(account, amountInBaseCurrency, baseCurrency, targetCurrency);
        return ResponseEntity.ok(accountConverter.convertToDTO(accountRepository.save(account)));
    }

    private void verifyInitialBalance(AccountHolder createdAccountHolder, BigDecimal initialBalance) {
        if (initialBalance.compareTo(BigDecimal.ZERO) == -1) {
            accountHolderService.delete(createdAccountHolder);
            throw new OperationNotAllowedException("Cannot create and account with negative balance.");
        }
    }
}
