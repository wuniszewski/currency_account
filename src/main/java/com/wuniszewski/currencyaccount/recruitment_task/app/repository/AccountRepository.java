package com.wuniszewski.currencyaccount.recruitment_task.app.repository;

import com.wuniszewski.currencyaccount.recruitment_task.app.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query(nativeQuery = true, value = "SELECT a.* FROM accounts a JOIN account_holders ah on a.account_holder_id = ah.id " +
            "WHERE ah.PESEL_number = ?1 LIMIT 1")
    Optional<Account> getAccountByPESELNumber(String PESEL_number);
}
