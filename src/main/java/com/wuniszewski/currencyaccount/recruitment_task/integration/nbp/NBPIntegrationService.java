package com.wuniszewski.currencyaccount.recruitment_task.integration.nbp;

import com.wuniszewski.currencyaccount.recruitment_task.app.model.Currency;
import com.wuniszewski.currencyaccount.recruitment_task.integration.nbp.exception.NBPServiceUnavailableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

@Service
public class NBPIntegrationService {

    private final RestTemplate restTemplate;

    private final NBPApiUrlProducer urlProducer;

    private final HttpStatus RESPONSE_STATUS_WHEN_TODAY_TABLE_NOT_RELEASED = HttpStatus.NOT_FOUND;

    @Autowired
    public NBPIntegrationService(RestTemplate restTemplate, NBPApiUrlProducer urlProducer) {
        this.restTemplate = restTemplate;
        this.urlProducer = urlProducer;
    }

    public NBPBuySellRecordDTO getBuySellRates(Currency currencyCode) {
        ResponseEntity<NBPBuySellRecordDTO> responseEntity = makeNBPApiRequest(LocalDate.now(), currencyCode);

        if (RESPONSE_STATUS_WHEN_TODAY_TABLE_NOT_RELEASED.equals(responseEntity.getStatusCode())) {
            responseEntity = makeNBPApiRequest(LocalDate.now().minusDays(1), currencyCode);
        }
        return responseEntity.getBody();
    }

    private ResponseEntity<NBPBuySellRecordDTO> makeNBPApiRequest(LocalDate rateDate, Currency currencyCode) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>("body", headers);

        URI rateExchangeRequestURI = urlProducer.prepareURIForRequest(rateDate, currencyCode);
        ResponseEntity<NBPBuySellRecordDTO> responseEntity =
                restTemplate.exchange(rateExchangeRequestURI, HttpMethod.GET, entity, NBPBuySellRecordDTO.class);
        verifyResponseExists(responseEntity);
        return responseEntity;
    }



    private void verifyResponseExists(ResponseEntity<NBPBuySellRecordDTO> responseEntity) {
        if (responseEntity == null) {
            throw new NBPServiceUnavailableException("NBP API is not available at the moment. Please, try again later.");
        }
    }
}
