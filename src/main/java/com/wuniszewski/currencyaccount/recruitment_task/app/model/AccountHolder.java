package com.wuniszewski.currencyaccount.recruitment_task.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "account_holders")
public class AccountHolder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "PESEL_number")
    private String PESEL_number;

    public AccountHolder() {
    }

    public AccountHolder(String firstName, String lastName, String PESEL_number) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.PESEL_number = PESEL_number;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPESEL_number() {
        return PESEL_number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountHolder that = (AccountHolder) o;
        return Objects.equals(id, that.id) && PESEL_number.equals(that.PESEL_number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, PESEL_number);
    }
}
