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
public class BuyExchangeStrategyPLNRelated implements NBPExchangeStrategy {

    private final NBPIntegrationService nbpIntegrationService;

    @Autowired
    public BuyExchangeStrategyPLNRelated(NBPIntegrationService nbpIntegrationService) {
        this.nbpIntegrationService = nbpIntegrationService;
    }

    @Override
    public Account exchangeCurrencyInAccount(Account account, BigDecimal amountInBaseCurrency, Currency baseCurrency, Currency targetCurrency) {
        SubAccount polishSubAccount = account.getSubAccount(baseCurrency);
        validateBalanceCapacity(amountInBaseCurrency, polishSubAccount);

        BigDecimal rate = getRate(nbpIntegrationService, targetCurrency, ExchangeMode.BUY);
        BigDecimal amountOfTargetCurrencyToAdd = amountInBaseCurrency.divide(rate, MathContext.DECIMAL128);


        SubAccount foreignSubAccount = account.getSubAccount(targetCurrency);
        foreignSubAccount.setBalance(foreignSubAccount.getBalance().add(amountOfTargetCurrencyToAdd));
        polishSubAccount.setBalance(polishSubAccount.getBalance().subtract(amountInBaseCurrency));
        return account;
    }
}
