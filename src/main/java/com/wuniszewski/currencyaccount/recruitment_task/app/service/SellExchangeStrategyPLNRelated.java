package com.wuniszewski.currencyaccount.recruitment_task.app.service;

import com.wuniszewski.currencyaccount.recruitment_task.app.model.Account;
import com.wuniszewski.currencyaccount.recruitment_task.app.model.Currency;
import com.wuniszewski.currencyaccount.recruitment_task.app.model.ExchangeMode;
import com.wuniszewski.currencyaccount.recruitment_task.app.model.SubAccount;
import com.wuniszewski.currencyaccount.recruitment_task.integration.nbp.service.NBPIntegrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.MathContext;

@Component
public class SellExchangeStrategyPLNRelated implements NBPExchangeStrategy {

    private final NBPIntegrationService nbpIntegrationService;

    @Autowired
    public SellExchangeStrategyPLNRelated(NBPIntegrationService nbpIntegrationService) {
        this.nbpIntegrationService = nbpIntegrationService;
    }

    @Override
    public Account exchangeCurrencyInAccount(Account account, BigDecimal amountInBaseCurrency, Currency baseCurrency, Currency targetCurrency) {
        SubAccount foreignSubAccount = account.getSubAccount(baseCurrency);
        validateBalanceCapacity(amountInBaseCurrency, foreignSubAccount);

        BigDecimal rate = getRate(nbpIntegrationService, baseCurrency, ExchangeMode.SELL);
        BigDecimal amountOfTargetCurrencyToAdd = amountInBaseCurrency.multiply(rate, MathContext.DECIMAL128);


        SubAccount polishSubAccount = account.getSubAccount(targetCurrency);
        polishSubAccount.setBalance(polishSubAccount.getBalance().add(amountOfTargetCurrencyToAdd));
        foreignSubAccount.setBalance(foreignSubAccount.getBalance().subtract(amountInBaseCurrency));
        return account;
    }
}
