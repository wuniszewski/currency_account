package com.wuniszewski.currencyaccount.recruitment_task.app.service;

import com.wuniszewski.currencyaccount.recruitment_task.app.model.Account;
import com.wuniszewski.currencyaccount.recruitment_task.app.model.Currency;

import java.math.BigDecimal;

public interface ExchangeStrategy {

    Account exchangeCurrencyInAccount(Account account, BigDecimal amount, Currency initialCurrency, Currency targetCurrency);
}
