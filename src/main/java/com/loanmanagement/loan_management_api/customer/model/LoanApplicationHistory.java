package com.loanmanagement.loan_management_api.customer.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "loan_application_history")
@Data
public class LoanApplicationHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loan_application_id")
    private int loanApplicationId;

    @Column(name = "customer_id", nullable = false)
    private int customerId;

    @Column(name = "loan_amount")
    private double loanAmount;

    @Column(name = "status") // Pending, Approved, Rejected
    private String status;


    @Column(name = "date_applied")
    private LocalDateTime dateApplied;

    public LoanApplicationHistory(int customerId, double loanAmount, LocalDateTime dateApplied,String status) {
        this.customerId = customerId;
        this.loanAmount = loanAmount;
        this.dateApplied = dateApplied;
        this.status = status;
    }
}
