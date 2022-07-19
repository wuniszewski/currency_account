package com.wuniszewski.currencyaccount.recruitment_task.integration.nbp.service;

import com.wuniszewski.currencyaccount.recruitment_task.app.model.Currency;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.time.LocalDate;

@Component
class NBPApiUrlProducer {

    private final String NBP_API_URL;

    private final String exchangeForeignRateUrlExtension = "/api/exchangerates/rates/c/%s/%s/";

    NBPApiUrlProducer(@Value("${com.wuniszewski.currencyaccount.nbp.api.url}") String NBPApiUrl) {
        this.NBP_API_URL = NBPApiUrl;
    }

    URI prepareURIForRequest(LocalDate rateRequestDate, Currency currencyCode) {
        String urlExtension = String.format(exchangeForeignRateUrlExtension, currencyCode.toString().toLowerCase(), rateRequestDate);
        return URI.create(NBP_API_URL + urlExtension);
    }
}
