package com.wuniszewski.currencyaccount.recruitment_task.app.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "sub_accounts")
public class SubAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Currency currency;

    private BigDecimal balance;

    public SubAccount() {
    }

    public SubAccount(Currency currency, BigDecimal balance) {
        this.currency = currency;
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubAccount that = (SubAccount) o;
        return Objects.equals(id, that.id) && currency == that.currency;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, currency);
    }
}
