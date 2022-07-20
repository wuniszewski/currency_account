package com.wuniszewski.currencyaccount.recruitment_task.integration.nbp.service;

import com.wuniszewski.currencyaccount.recruitment_task.app.model.Currency;
import com.wuniszewski.currencyaccount.recruitment_task.integration.nbp.dto.NBPBuySellRecordDTO;
import com.wuniszewski.currencyaccount.recruitment_task.integration.nbp.exception.NBPServiceUnavailableException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NBPIntegrationServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private NBPApiUrlProducer urlProducer;

    @InjectMocks
    private NBPIntegrationService integrationService;

    @Test
    public void makeNBPApiRequest_shouldThrowNBPServiceUnavailableExcWhenAPIUnavailable() {
        //given
        Currency currency = Currency.USD;

        //when
        when(restTemplate.exchange(any(), any(), any(), eq(NBPBuySellRecordDTO.class)))
                .thenReturn(null);

        //then
        assertThrows(NBPServiceUnavailableException.class, () -> integrationService.getBuySellRates(currency));
    }
}