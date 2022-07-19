package com.wuniszewski.currencyaccount.recruitment_task.app.util;

import com.wuniszewski.currencyaccount.recruitment_task.app.exception.OperationNotAllowedException;
import com.wuniszewski.currencyaccount.recruitment_task.app.model.Currency;
import com.wuniszewski.currencyaccount.recruitment_task.app.service.BuyExchangeStrategyPLNRelated;
import com.wuniszewski.currencyaccount.recruitment_task.app.service.ExchangeStrategy;
import com.wuniszewski.currencyaccount.recruitment_task.app.service.SellExchangeStrategyPLNRelated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExchangeStrategyProvider {

    private final BuyExchangeStrategyPLNRelated buyExchangeStrategyPLNRelated;

    private final SellExchangeStrategyPLNRelated sellExchangeStrategyPLNRelated;

    @Autowired
    public ExchangeStrategyProvider(BuyExchangeStrategyPLNRelated buyExchangeStrategyPLNRelated, SellExchangeStrategyPLNRelated sellExchangeStrategyPLNRelated) {
        this.buyExchangeStrategyPLNRelated = buyExchangeStrategyPLNRelated;
        this.sellExchangeStrategyPLNRelated = sellExchangeStrategyPLNRelated;
    }

    public ExchangeStrategy getExchangeStrategy(Currency baseCurrency, Currency targetCurrency) {
        ExchangeStrategy appropriateStrategy;
        if (Currency.PLN.equals(baseCurrency) || Currency.PLN.equals(targetCurrency)) {
            if (Currency.PLN.equals(baseCurrency)) {
                appropriateStrategy = buyExchangeStrategyPLNRelated;
            } else {
                appropriateStrategy = sellExchangeStrategyPLNRelated;
            }
        } else {
            //Implementation of exchange between foreign currencies is out of scope.
            // It would require another ExchangeStrategy integrating with another rate API.
            throw new OperationNotAllowedException("Exchange between foreign currencies is not supported.");
        }
        return appropriateStrategy;
    }
}
