package com.wuniszewski.currencyaccount.recruitment_task.integration.nbp.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class NBPBuySellRecordDTO {

    private String table;

    private String currency;

    private String code;

    private Set<NBPRateDTO> rates;
}
