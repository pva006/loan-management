package com.loanmanagement.loan_management_api.customer.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "loan")
@Data
public class Loan {

    @Id
    @Column(name = "loan_id")
    private int loanId;

    @Column(name = "loan_status")
    private String loanStatus;

    @Column(name = "outstanding_amount")
    private Double outstandingAmount;

    @Column(name = "loan_amount")
    private Double loanAmount;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonBackReference
    private Customer customer;


}
