package com.wuniszewski.currencyaccount.recruitment_task.app.dto;

import java.math.BigDecimal;

public record CreateAccountRequestBodyDTO(String firstName, String lastName, String PESEL_number,
                                          BigDecimal initialBalance) {
}
