package com.wuniszewski.currencyaccount.recruitment_task.integration.nbp.service;

import com.wuniszewski.currencyaccount.recruitment_task.app.model.Currency;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.net.URI;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class NBPApiUrlProducerTest {

    @InjectMocks
    private NBPApiUrlProducer urlProducer;

    @Test
    public void prepareURIForRequest_shouldCreateValidUrl() {
        //given
        ReflectionTestUtils.setField(urlProducer, "NBP_API_URL", "https://api.nbp.pl");
        LocalDate testDate = LocalDate.of(2020, 01, 01);

        //when
        URI uri = urlProducer.prepareURIForRequest(testDate, Currency.USD);

        //then
        assertEquals("https://api.nbp.pl/api/exchangerates/rates/c/usd/2020-01-01/", uri.toString());
    }
}