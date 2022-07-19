package com.wuniszewski.currencyaccount.recruitment_task.app.dto;

import com.wuniszewski.currencyaccount.recruitment_task.app.model.Currency;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class SubAccountDTO {

    private Long id;

    private Currency currency;

    private BigDecimal balance;
}
