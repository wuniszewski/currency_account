package com.wuniszewski.currencyaccount.recruitment_task.app.controller;

import com.wuniszewski.currencyaccount.recruitment_task.app.dto.AccountDTO;
import com.wuniszewski.currencyaccount.recruitment_task.app.dto.CreateAccountRequestBodyDTO;
import com.wuniszewski.currencyaccount.recruitment_task.app.dto.ExchangeRequestBodyDTO;
import com.wuniszewski.currencyaccount.recruitment_task.app.service.AccountManagementService;
import com.wuniszewski.currencyaccount.recruitment_task.app.service.AccountQueryService;
import com.wuniszewski.currencyaccount.recruitment_task.app.service.ExchangeStrategy;
import com.wuniszewski.currencyaccount.recruitment_task.app.util.ExchangeStrategyProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("currencyaccount")
public class AccountController {

    private final AccountManagementService accountManagementService;

    private final AccountQueryService accountQueryService;

    private final ExchangeStrategyProvider exchangeStrategyProvider;

    @Autowired
    public AccountController(AccountManagementService accountManagementService,
                             AccountQueryService accountQueryService,
                             ExchangeStrategyProvider exchangeStrategyProvider) {
        this.accountManagementService = accountManagementService;
        this.accountQueryService = accountQueryService;
        this.exchangeStrategyProvider = exchangeStrategyProvider;
    }

    @PostMapping("/create")
    public ResponseEntity createAccount(@RequestBody CreateAccountRequestBodyDTO createAccountRequest) {
        return accountManagementService.create(
                createAccountRequest.firstName(),
                createAccountRequest.lastName(),
                createAccountRequest.PESEL_number(),
                createAccountRequest.initialBalance());
    }

    @PutMapping("/exchange")
    public ResponseEntity<AccountDTO> exchangeCurrency(@RequestBody ExchangeRequestBodyDTO exchangeRequest) {
        ExchangeStrategy exchangeStrategy = exchangeStrategyProvider.getExchangeStrategy(exchangeRequest.baseCurrency(),
                exchangeRequest.targetCurrency());
        return accountManagementService.exchangeCurrency(
                exchangeRequest.PESEL_number(),
                exchangeRequest.amountInBaseCurrency(),
                exchangeRequest.baseCurrency(),
                exchangeRequest.targetCurrency(),
                exchangeStrategy);
    }

    @GetMapping("/balance")
    public ResponseEntity<AccountDTO> getAccountBalance(@RequestBody String PESEL_number) {
        return accountQueryService.getBalance(PESEL_number);
    }
}
