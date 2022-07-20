package com.wuniszewski.currencyaccount.recruitment_task.app.service;

import com.wuniszewski.currencyaccount.recruitment_task.app.converter.DTOConverter;
import com.wuniszewski.currencyaccount.recruitment_task.app.dto.AccountDTO;
import com.wuniszewski.currencyaccount.recruitment_task.app.exception.AccountDoesNotExistException;
import com.wuniszewski.currencyaccount.recruitment_task.app.exception.OperationNotAllowedException;
import com.wuniszewski.currencyaccount.recruitment_task.app.model.Account;
import com.wuniszewski.currencyaccount.recruitment_task.app.model.AccountHolder;
import com.wuniszewski.currencyaccount.recruitment_task.app.model.Currency;
import com.wuniszewski.currencyaccount.recruitment_task.app.repository.AccountRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountManagementServiceTest {

    private Set<AccountHolder> accountHolderTableMock = new HashSet<>();

    private Set<Account> accountTableMock = new HashSet<>();

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountHolderService accountHolderService;

    @Mock
    private DTOConverter<Account, AccountDTO> converter;

    @Mock
    private ExchangeStrategy exchangeStrategy;

    @InjectMocks
    private AccountManagementService accountManagementService;

    @AfterEach
    private void clearDB() {
        accountHolderTableMock.clear();
        accountTableMock.clear();
    }

    @Test
    public void create_shouldThrowOperationNotAllowedExcGivenNegativeInitialBalance() {
        //given
        AccountHolder accountHolder = new AccountHolder("John", "Smith", "00010100000");

        //when
        when(accountHolderService.create(anyString(), anyString(), anyString())).thenReturn(accountHolder);
        doNothing().when(accountHolderService).delete(any(AccountHolder.class));

        //then
        assertThrows(OperationNotAllowedException.class,
                () -> accountManagementService.create("John", "Smith", "0001010000", BigDecimal.valueOf(-1)));
    }

    @Test
    public void create_shouldRollbackCreatedAccountHolder() throws OperationNotAllowedException {
        //given
        AccountHolder accountHolder = new AccountHolder("John", "Smith", "00010100000");

        //when
        doAnswer(invocation -> {
            accountHolderTableMock.add(accountHolder);
            return null;
        }).when(accountHolderService.create(anyString(), anyString(), anyString()));
        doAnswer(invocation -> {
            accountHolderTableMock.remove(accountHolder);
            return null;
        }).when(accountHolderService).delete(any(AccountHolder.class));

        //then
        assertThrows(OperationNotAllowedException.class,
                () -> accountManagementService.create("John", "Smith", "0001010000", BigDecimal.valueOf(-1)));
        assertFalse(accountHolderTableMock.contains(accountHolder));
    }

    @Test
    public void create_shouldCreateAccountGivenCorrectData() {
        //given
        AccountHolder accountHolder = new AccountHolder("John", "Smith", "00010100000");
        Account account = new Account(accountHolder, BigDecimal.ONE);

        //when
        doAnswer(invocation -> {
            accountHolderTableMock.add(accountHolder);
            return accountHolder;
        }).when(accountHolderService).create(anyString(), anyString(), anyString());
        doAnswer(invocation -> {
            accountTableMock.add(account);
            return account;
        }).when(accountRepository).save(any(Account.class));
        ResponseEntity responseEntity = accountManagementService.create("John", "Smith", "00010100000", BigDecimal.ONE);

        //then
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertTrue(accountTableMock.contains(account));
    }

    @Test
    public void exchange_shouldThrowAccountDoesNotExistExcWhenAccountMissing() {
        //given
        //when
        when(accountRepository.getAccountByPESELNumber(anyString())).thenReturn(Optional.empty());

        //then
        assertThrows(AccountDoesNotExistException.class, () ->
                accountManagementService.exchangeCurrency("00010100000", BigDecimal.TEN, Currency.PLN, Currency.USD, exchangeStrategy));
    }
}