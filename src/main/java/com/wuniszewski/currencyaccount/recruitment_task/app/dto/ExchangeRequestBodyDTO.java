package com.wuniszewski.currencyaccount.recruitment_task.app.dto;

import com.wuniszewski.currencyaccount.recruitment_task.app.model.Currency;

import java.math.BigDecimal;

public record ExchangeRequestBodyDTO(String PESEL_number, BigDecimal amountInBaseCurrency,
                                     Currency baseCurrency, Currency targetCurrency) {
}
