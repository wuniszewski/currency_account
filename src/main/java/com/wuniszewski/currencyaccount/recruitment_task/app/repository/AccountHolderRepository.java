package com.wuniszewski.currencyaccount.recruitment_task.app.repository;

import com.wuniszewski.currencyaccount.recruitment_task.app.model.AccountHolder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountHolderRepository extends JpaRepository<AccountHolder, Long> {

    Optional<AccountHolder> getAccountHolderByPESEL_number(String PESEL_number);

}
