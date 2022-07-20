package com.wuniszewski.currencyaccount.recruitment_task.app.service;

import com.wuniszewski.currencyaccount.recruitment_task.app.exception.OperationNotAllowedException;
import com.wuniszewski.currencyaccount.recruitment_task.app.model.Currency;
import com.wuniszewski.currencyaccount.recruitment_task.app.model.ExchangeMode;
import com.wuniszewski.currencyaccount.recruitment_task.app.model.SubAccount;
import com.wuniszewski.currencyaccount.recruitment_task.integration.nbp.dto.NBPBuySellRecordDTO;
import com.wuniszewski.currencyaccount.recruitment_task.integration.nbp.exception.NBPServiceUnavailableException;
import com.wuniszewski.currencyaccount.recruitment_task.integration.nbp.service.NBPIntegrationService;

import java.math.BigDecimal;
import java.util.Optional;

public interface NBPExchangeStrategy extends ExchangeStrategy {

    default BigDecimal getRate(NBPIntegrationService integrationService, Currency desiredRateCurrency, ExchangeMode exchangeMode) {
        NBPBuySellRecordDTO buySellRates = integrationService.getBuySellRates(desiredRateCurrency);
        Optional<BigDecimal> rateOpt = buySellRates.getRates().stream()
                .map(nbpRateDTO -> {
                    if (ExchangeMode.BUY.equals(exchangeMode)) {
                        return nbpRateDTO.getAsk();
                    } else {
                        return nbpRateDTO.getBid();
                    }
                })
                .findFirst();
        if (rateOpt.isEmpty()) {
            throw new NBPServiceUnavailableException("No rates found in the API response.");
        }
        return rateOpt.get();
    }

    default void validateBalanceCapacity(BigDecimal amountToSubtract, SubAccount accountForSubtraction) {
        if (amountToSubtract.compareTo(accountForSubtraction.getBalance()) == 1) {
            throw new OperationNotAllowedException("Not enough balance to perform the operation.");
        }
    }
}
