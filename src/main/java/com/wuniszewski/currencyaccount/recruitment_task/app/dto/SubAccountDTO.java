package com.wuniszewski.currencyaccount.recruitment_task.app.dto;

import com.wuniszewski.currencyaccount.recruitment_task.app.model.Currency;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
public class SubAccountDTO {

    private Long id;

    private Currency currency;

    private BigDecimal balance;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubAccountDTO that = (SubAccountDTO) o;
        return Objects.equals(id, that.id) && currency == that.currency;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, currency);
    }
}
