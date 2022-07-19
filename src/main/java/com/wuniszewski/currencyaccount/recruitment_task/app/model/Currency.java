package com.wuniszewski.currencyaccount.recruitment_task.app.model;

public enum Currency {
    PLN ("PLN"),
    USD ("USD");

    private final String currencyCode;

    Currency(String currencyCode) {
        this.currencyCode = currencyCode;
    }
}
