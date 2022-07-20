package com.wuniszewski.currencyaccount.recruitment_task.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
public class AccountDTO {

    private Long id;

    @JsonProperty("account_holder")
    private AccountHolderDTO accountHolder;

    @JsonProperty("sub_accounts")
    private Set<SubAccountDTO> subAccounts = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountDTO that = (AccountDTO) o;
        return Objects.equals(id, that.id) && accountHolder.equals(that.accountHolder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accountHolder);
    }
}
