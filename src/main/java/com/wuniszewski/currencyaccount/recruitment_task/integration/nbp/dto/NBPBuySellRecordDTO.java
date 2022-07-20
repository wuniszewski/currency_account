package com.wuniszewski.currencyaccount.recruitment_task.integration.nbp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NBPBuySellRecordDTO {

    private String table;

    private String currency;

    private String code;

    private Set<NBPRateDTO> rates;
}
