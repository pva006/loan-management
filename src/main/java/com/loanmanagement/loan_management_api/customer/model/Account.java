package com.loanmanagement.loan_management_api.customer.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "Account")
@Data
public class Account {


    @Id
    @Column(name = "account_id")
    private int accountId;
    @Column(name = "customer_id")
    private int customerNumber;
    @Column(name = "bank_name")
    private String bankName;
    @Column(name = "account_type")
    private String accountType;
    @Column(name = "branch")
    private String branch;
    @Column(name = "balance")
    private double balance;
    @Column(name = "yearly_income")
    private double yearlyIncome;

    @Column(name = "creation_date")
    private LocalDateTime accountCreatedDate;


    @OneToOne(mappedBy = "account")
    @JsonBackReference
    private Customer customer;



}
