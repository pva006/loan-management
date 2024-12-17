package com.loanmanagement.loan_management_api.customer.controller;


import com.loanmanagement.loan_management_api.customer.model.CustomerDetails;
import com.loanmanagement.loan_management_api.customer.model.Loan;
import com.loanmanagement.loan_management_api.customer.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoanController {


    @Autowired
    LoanRepository loanRepository;

    @GetMapping("/loans/{id}")
    public ResponseEntity<Loan> getLoanDetails(@PathVariable int id) {
        Loan loan  = loanRepository.findById(id).get();
        return ResponseEntity.ok(loan);

    }



}
