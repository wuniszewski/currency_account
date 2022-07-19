package com.wuniszewski.currencyaccount.recruitment_task.app.controller;

import com.wuniszewski.currencyaccount.recruitment_task.app.model.Currency;
import com.wuniszewski.currencyaccount.recruitment_task.app.service.AccountManagementService;
import com.wuniszewski.currencyaccount.recruitment_task.app.service.AccountQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("currencyaccount")
public class AccountController {

    private final AccountManagementService accountManagementService;

    private final AccountQueryService accountQueryService;

    @Autowired
    public AccountController(AccountManagementService accountManagementService, AccountQueryService accountQueryService) {
        this.accountManagementService = accountManagementService;
        this.accountQueryService = accountQueryService;
    }

    @PostMapping("/create")
    public ResponseEntity createAccount(@RequestParam String firstName,
                                        @RequestParam String lastName,
                                        @RequestParam String PESEL_number,
                                        @RequestParam BigDecimal initialBalance) {
        accountManagementService.create(firstName, lastName, PESEL_number, initialBalance);


    }

    @PutMapping("/exchange/buy")
    public ResponseEntity exchangeByPreviousCurrencyAmount(@RequestParam String PESEL_number,
                                                           @RequestParam Long amountInBoughtCurrency,
                                                           @RequestParam Currency soldCurrency,
                                                           @RequestParam Currency boughtCurrency) {


        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/exchange/sell")
    public ResponseEntity exchangeByTargetCurrencyAmount(@RequestParam String PESEL_number,
                                                         @RequestParam Long amountInBoughtCurrency,
                                                         @RequestParam Currency soldCurrency,
                                                         @RequestParam Currency boughtCurrency) {


    }

    @GetMapping("/balance")
    public ResponseEntity getAccountBalance(@RequestParam String PESEL_number) {

        return accountQueryService.getBalance(PESEL_number);
    }

}
