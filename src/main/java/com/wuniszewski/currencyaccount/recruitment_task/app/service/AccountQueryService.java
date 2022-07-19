package com.wuniszewski.currencyaccount.recruitment_task.app.service;

import com.wuniszewski.currencyaccount.recruitment_task.app.converter.DTOConverter;
import com.wuniszewski.currencyaccount.recruitment_task.app.dto.AccountDTO;
import com.wuniszewski.currencyaccount.recruitment_task.app.exception.AccountDoesNotExistException;
import com.wuniszewski.currencyaccount.recruitment_task.app.model.Account;
import com.wuniszewski.currencyaccount.recruitment_task.app.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountQueryService {

    private final AccountRepository accountRepository;

    private final DTOConverter<AccountDTO, Account> accountConverter;

    @Autowired
    public AccountQueryService(AccountRepository accountRepository,
                               @Qualifier("accountConverter") DTOConverter<AccountDTO, Account> accountConverter) {
        this.accountRepository = accountRepository;
        this.accountConverter = accountConverter;
    }

    public ResponseEntity<AccountDTO> getBalance(String PESELNumber) {
        Optional<Account> accountByPESELNumber = accountRepository.getAccountByPESELNumber(PESELNumber);
        if (accountByPESELNumber.isEmpty()) {
            throw new AccountDoesNotExistException("Account with given PESEL number doesn't exist.");
        }
        Account account = accountByPESELNumber.get();
        return ResponseEntity.ok(accountConverter.convertToDTO(account));
    }
}
