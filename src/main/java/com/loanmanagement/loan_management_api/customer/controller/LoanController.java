package com.loanmanagement.loan_management_api.customer.controller;


import com.loanmanagement.loan_management_api.customer.model.*;
import com.loanmanagement.loan_management_api.customer.repository.LoanApplicationHistoryRepository;
import com.loanmanagement.loan_management_api.customer.repository.LoanRepository;
import com.loanmanagement.loan_management_api.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class LoanController {


    @Autowired
    LoanRepository loanRepository;
    @Autowired
    private LoanApplicationHistoryRepository historyRepository;

    @Autowired
    private CustomerService customerService;

    @GetMapping("/history")
    public List<LoanApplicationHistory> getLoanApplicationHistory() {
        return historyRepository.findAll();
    }

    @GetMapping("/loans/{id}")
    public ResponseEntity<Loan> getLoanDetails(@PathVariable int id) {
        Loan loan  = loanRepository.findById(id).get();
        return ResponseEntity.ok(loan);
    }

    @PostMapping("/calculate/{customerId}")
    public ResponseEntity<?> calculateMaxLoan (@PathVariable int customerId){
       Optional<Customer>  customerOptional = customerService.findCustomerById(customerId);
   double maxLoanAmount ;
        if (customerOptional.isEmpty()){
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");
        }

        Customer customer = customerOptional.get();
        CustomerDetails customerDetails = customer.getCustomerDetails();
        Account account = customer.getAccount();

        double idr = (account.getBalance() / account.getYearlyIncome() ) * 100 ;

        if (customerDetails.getCreditScore() > 750){
            maxLoanAmount = 50000;
        } else if (customerDetails.getCreditScore() > 700){
            maxLoanAmount = 30000;
        } else {
            maxLoanAmount=   0 ;
        }

         if (idr < 40) {
             maxLoanAmount =  maxLoanAmount * 0.75;
        } else {
             maxLoanAmount=   0 ;
        }

        return ResponseEntity.ok("Maximum Loan Amount : $" +maxLoanAmount);
    }




}
