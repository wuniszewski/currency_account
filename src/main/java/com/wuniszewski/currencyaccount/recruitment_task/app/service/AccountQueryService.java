package com.wuniszewski.currencyaccount.recruitment_task.app.service;

import com.wuniszewski.currencyaccount.recruitment_task.app.model.Account;
import com.wuniszewski.currencyaccount.recruitment_task.app.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountQueryService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountQueryService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public ResponseEntity getBalance(String PESEL_number) {
        Optional<Account> accountByPESELNumber = accountRepository.getAccountByPESELNumber(PESEL_number);
        if (accountByPESELNumber.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(accountByPESELNumber.get());
        }
    }
}
