package com.wuniszewski.currencyaccount.recruitment_task.integration.nbp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NBPRateDTO {

    private String no;

    private LocalDate effectiveDate;

    private BigDecimal bid;

    private BigDecimal ask;
}
