package com.wuniszewski.currencyaccount.recruitment_task.app.model;

import com.wuniszewski.currencyaccount.recruitment_task.app.exception.OperationNotAllowedException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;


class AccountTest {

    @Test
    public void shouldCreateTwoSubAccountsDuringCreation () {
        //given
        AccountHolder testAccountHolder = new AccountHolder("John", "Smith", "00010100000");
        BigDecimal initialPLNBalance = BigDecimal.ZERO;

        //when
        Account createdAccount = new Account(testAccountHolder, initialPLNBalance);

        //then
        assertEquals(2, createdAccount.getSubAccounts().size());
        assertEquals(new SubAccount(Currency.PLN, BigDecimal.ZERO), createdAccount.getSubAccount(Currency.PLN));
        assertEquals(new SubAccount(Currency.USD, BigDecimal.ZERO), createdAccount.getSubAccount(Currency.USD));
    }

    @Test
    public void getSubAccount_shouldThrowOperationNotAllowedExceptionWhenSubAccountMissing () {
        //given
        AccountHolder testAccountHolder = new AccountHolder("John", "Smith", "00010100000");
        Account createdAccount = new Account(testAccountHolder, BigDecimal.ZERO);
        createdAccount.getSubAccounts().remove(new SubAccount(Currency.USD, BigDecimal.ZERO));

        //then
        assertThrows(OperationNotAllowedException.class, () -> createdAccount.getSubAccount(Currency.USD));
    }
}