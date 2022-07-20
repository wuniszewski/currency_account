package com.wuniszewski.currencyaccount.recruitment_task.app.service;

import com.wuniszewski.currencyaccount.recruitment_task.app.exception.AccountHolderAlreadyExistsException;
import com.wuniszewski.currencyaccount.recruitment_task.app.exception.PESELNumberFormatInvalidException;
import com.wuniszewski.currencyaccount.recruitment_task.app.exception.UnderageAccountHolderCandidateException;
import com.wuniszewski.currencyaccount.recruitment_task.app.model.AccountHolder;
import com.wuniszewski.currencyaccount.recruitment_task.app.repository.AccountHolderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountHolderServiceTest {

    @Mock
    private AccountHolderRepository repository;

    @InjectMocks
    private AccountHolderService accountHolderService;

    @Test
    public void create_shouldThrowPESELNumberFormatInvalidExceptionGivenNaNPESEL() {
        //given
        String PESELContainingLetters = "0123456789D";
        String PESELWithWrongNumberOfChar = "0123456789";

        //then
        assertThrows(PESELNumberFormatInvalidException.class, () ->
                accountHolderService.create("John", "Smith", PESELContainingLetters));
    }

    @Test
    public void create_shouldThrowPESELNumberFormatInvalidExceptionGivenPESELWithWrongLength() {
        //given
        String PESELWithWrongNumberOfChar = "0123456789";

        //then
        assertThrows(PESELNumberFormatInvalidException.class, () ->
                accountHolderService.create("John", "Smith", PESELWithWrongNumberOfChar));
    }

    @Test
    public void create_shouldThrowUnderageAccountHolderCandidateExcWhenUnderageAccountCandidate() {
        //given
        String lastTwoDigitsFromYear17BeforeCurrent = String.valueOf(LocalDate.now().getYear() - 17).substring(2, 4);
        String underagePersonPESEL = lastTwoDigitsFromYear17BeforeCurrent + "210100000";

        //then
        assertThrows(UnderageAccountHolderCandidateException.class, () ->
                accountHolderService.create("John", "Smith", underagePersonPESEL));
    }

    @Test
    public void create_shouldThrowAccountHolderAlreadyExistsExcGivenAccountHolderPresentInDB() {
        //given
        AccountHolder accountHolder = new AccountHolder("John", "Smith", "00010100000");

        //when
        when(repository.getAccountHolderByPESELNumber(anyString())).thenReturn(Optional.of(accountHolder));

        //then
        assertThrows(AccountHolderAlreadyExistsException.class, () ->
                accountHolderService.create(accountHolder.getFirstName(), accountHolder.getLastName(), accountHolder.getPESELNumber()));
    }

    @Test
    public void create_shouldCreateHolderGivenCorrectData() {
        //given
        AccountHolder accountHolder = new AccountHolder("John", "Smith", "00010100000");

        //when
        when(repository.getAccountHolderByPESELNumber(anyString())).thenReturn(Optional.empty());
        when(repository.save(any(AccountHolder.class))).thenReturn(accountHolder);

        //then
        assertEquals(accountHolder, accountHolderService.create(accountHolder.getFirstName(),
                accountHolder.getLastName(), accountHolder.getPESELNumber()));
    }
}