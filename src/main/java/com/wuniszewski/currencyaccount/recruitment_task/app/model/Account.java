package com.wuniszewski.currencyaccount.recruitment_task.app.model;

import com.wuniszewski.currencyaccount.recruitment_task.app.exception.OperationNotAllowedException;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_holder_id", referencedColumnName = "id")
    private AccountHolder accountHolder;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "account_id")
    private Set<SubAccount> subAccounts = new HashSet<>();

    public Account() {
    }

    public Account(AccountHolder accountHolder, BigDecimal initialBalance) {
        this.accountHolder = accountHolder;
        this.subAccounts.addAll(prepareInitialSubAccounts(initialBalance));
    }

    private Set<SubAccount> prepareInitialSubAccounts(BigDecimal initialBalance) {
        SubAccount polishSubAccount = new SubAccount(Currency.PLN, initialBalance);
        SubAccount americanSubAccount = new SubAccount(Currency.USD, BigDecimal.ZERO);
        return Set.of(polishSubAccount, americanSubAccount);
    }

    public Long getId() {
        return id;
    }

    public AccountHolder getAccountHolder() {
        return accountHolder;
    }

    public Set<SubAccount> getSubAccounts() {
        return subAccounts;
    }

    public SubAccount getSubAccount(Currency currency) {
        return subAccounts.stream()
                .filter(subAccount -> currency.equals(subAccount.getCurrency()))
                .findFirst().orElseThrow(() -> new OperationNotAllowedException(String.format("Sub-account in {%s} doesn't exist.", currency)));
    }

    public void addSubAccount(SubAccount subAccount) {
        this.subAccounts.add(subAccount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id) && accountHolder.equals(account.accountHolder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accountHolder);
    }
}
