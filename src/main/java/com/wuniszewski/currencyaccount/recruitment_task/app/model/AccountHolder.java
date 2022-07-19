package com.wuniszewski.currencyaccount.recruitment_task.app.model;

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
    private String PESELNumber;

    public AccountHolder() {
    }

    public AccountHolder(String firstName, String lastName, String PESELNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.PESELNumber = PESELNumber;
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

    public String getPESELNumber() {
        return PESELNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountHolder that = (AccountHolder) o;
        return Objects.equals(id, that.id) && PESELNumber.equals(that.PESELNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, PESELNumber);
    }
}
