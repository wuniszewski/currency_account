package com.wuniszewski.currencyaccount.recruitment_task.app.service;

import com.wuniszewski.currencyaccount.recruitment_task.app.model.Account;
import com.wuniszewski.currencyaccount.recruitment_task.app.model.AccountHolder;
import com.wuniszewski.currencyaccount.recruitment_task.app.model.Currency;
import com.wuniszewski.currencyaccount.recruitment_task.integration.nbp.dto.NBPBuySellRecordDTO;
import com.wuniszewski.currencyaccount.recruitment_task.integration.nbp.dto.NBPRateDTO;
import com.wuniszewski.currencyaccount.recruitment_task.integration.nbp.service.NBPIntegrationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SellExchangeStrategyPLNRelatedTest {

    @Mock
    private NBPIntegrationService integrationService;

    @InjectMocks
    private SellExchangeStrategyPLNRelated exchangeStrategy;

    @Test
    public void exchangeCurrencyInAccount_shouldCorrectlyExchangeMoneyGivenCorrectData() {
        //given
        NBPRateDTO rateDTO = new NBPRateDTO("138/C/NBP/2022", LocalDate.of(2022, 07, 19), BigDecimal.valueOf(2), BigDecimal.valueOf(5));
        NBPBuySellRecordDTO buySellRecordDTO = new NBPBuySellRecordDTO("C", "dolar amerykański", "USD", Set.of(rateDTO));
        AccountHolder accountHolder = new AccountHolder("John", "Smith", "00010100000");
        Account account = new Account(accountHolder, BigDecimal.ZERO);
        account.getSubAccounts().forEach(subAccount -> subAccount.setBalance(BigDecimal.TEN));

        //when
        when(integrationService.getBuySellRates(Currency.USD)).thenReturn(buySellRecordDTO);
        exchangeStrategy.exchangeCurrencyInAccount(account, BigDecimal.TEN, Currency.USD, Currency.PLN);

        //then
        assertEquals(BigDecimal.ZERO, account.getSubAccount(Currency.USD).getBalance());
        assertEquals(BigDecimal.valueOf(30), account.getSubAccount(Currency.PLN).getBalance());
    }
}