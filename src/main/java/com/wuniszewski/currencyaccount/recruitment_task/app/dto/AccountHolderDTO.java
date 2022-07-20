package com.wuniszewski.currencyaccount.recruitment_task.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class AccountHolderDTO {

    private Long id;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("PESEL_number")
    private String PESELNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountHolderDTO that = (AccountHolderDTO) o;
        return Objects.equals(id, that.id) && PESELNumber.equals(that.PESELNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, PESELNumber);
    }
}
