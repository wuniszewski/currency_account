package com.wuniszewski.currencyaccount.recruitment_task.app.converter;

import com.wuniszewski.currencyaccount.recruitment_task.app.dto.AccountDTO;
import com.wuniszewski.currencyaccount.recruitment_task.app.dto.AccountHolderDTO;
import com.wuniszewski.currencyaccount.recruitment_task.app.dto.SubAccountDTO;
import com.wuniszewski.currencyaccount.recruitment_task.app.model.Account;
import com.wuniszewski.currencyaccount.recruitment_task.app.model.AccountHolder;
import com.wuniszewski.currencyaccount.recruitment_task.app.model.Currency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
class AccountConverterTest {

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private AccountConverter accountConverter;

    private Account account;

    private AccountDTO accountDTO;

    @BeforeEach
    private void setUp() {
        AccountHolder testAccountHolder = new AccountHolder("John", "Smith", "00000000000");
        this.account = new Account(testAccountHolder, BigDecimal.ZERO);
        AccountHolderDTO testAccountHolderDTO = new AccountHolderDTO();
        testAccountHolderDTO.setFirstName("John");
        testAccountHolderDTO.setLastName("Smith");
        testAccountHolderDTO.setPESELNumber("00010100000");
        SubAccountDTO polishSubAccountDTO = new SubAccountDTO();
        polishSubAccountDTO.setCurrency(Currency.PLN);
        polishSubAccountDTO.setBalance(BigDecimal.ZERO);
        SubAccountDTO americanSubAccountDTO = new SubAccountDTO();
        americanSubAccountDTO.setCurrency(Currency.USD);
        americanSubAccountDTO.setBalance(BigDecimal.ZERO);
        Set<SubAccountDTO> subAccounts = new HashSet<>();
        subAccounts.add(polishSubAccountDTO);
        subAccounts.add(americanSubAccountDTO);
        accountDTO = new AccountDTO();
        accountDTO.setAccountHolder(testAccountHolderDTO);
        accountDTO.setSubAccounts(subAccounts);
    }

    @Test
    public void convertToDTO_shouldReturnCorrectDTOGivenEntity() {
        //when
        when(modelMapper.map(account, AccountDTO.class)).thenReturn(accountDTO);

        //then
        assertEquals(accountDTO, accountConverter.convertToDTO(account));
    }

    @Test
    public void convertToDTO_shouldThrowIllegalArgExceptionGivenNull() {
        //when
        when(modelMapper.map(null, AccountDTO.class)).thenThrow(IllegalArgumentException.class);

        //then
        assertThrows(IllegalArgumentException.class, () -> accountConverter.convertToDTO(null));
    }

}