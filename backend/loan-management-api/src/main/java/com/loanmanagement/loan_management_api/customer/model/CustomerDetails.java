package com.loanmanagement.loan_management_api.customer.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name="customer_details")
public class CustomerDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_details_id")
    private  int customerDetailsId;

    @Column(name = "income")
    private int income ;

    @Column(name = "credit_score")
    private int creditScore;

    @OneToOne
    @JoinColumn(name = "customer_id",nullable = false,unique = true)
    @JsonBackReference
    private Customer customer;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getCustomerDetailsId() {
        return customerDetailsId;
    }

    public void setCustomerDetailsId(int customerDetailsId) {
        this.customerDetailsId = customerDetailsId;
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }

    public int getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(int creditScore) {
        this.creditScore = creditScore;
    }
}
