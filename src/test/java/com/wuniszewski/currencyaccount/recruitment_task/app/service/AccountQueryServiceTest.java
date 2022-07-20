package com.wuniszewski.currencyaccount.recruitment_task.app.service;

import com.wuniszewski.currencyaccount.recruitment_task.app.exception.AccountDoesNotExistException;
import com.wuniszewski.currencyaccount.recruitment_task.app.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountQueryServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountQueryService queryService;

    @Test
    public void getBalance_shouldThrowAccountDoesNotExistExcWhenNoAccount () {
        //when
        when(accountRepository.getAccountByPESELNumber(anyString())).thenReturn(Optional.empty());

        //then
        assertThrows(AccountDoesNotExistException.class, () -> queryService.getBalance("00010100000"));
    }

}