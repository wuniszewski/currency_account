package com.wuniszewski.currencyaccount.recruitment_task.integration.nbp.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class NBPRateDTO {

    private String no;

    private LocalDate effectiveDate;

    private BigDecimal bid;

    private BigDecimal ask;
}
