package com.loanmanagement.loan_management_api.customer.controller;

import com.loanmanagement.loan_management_api.customer.model.Customer;
import com.loanmanagement.loan_management_api.customer.model.CustomerDetails;
import com.loanmanagement.loan_management_api.customer.model.LoanEligibility;
import com.loanmanagement.loan_management_api.customer.service.CustomerDetailsService;
import com.loanmanagement.loan_management_api.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerDetailsService customerDetailsService;

    @GetMapping("/eligibility/{id}/{amount}")
    public ResponseEntity<LoanEligibility> checkEligibility(@PathVariable int id,@PathVariable double amount) {
        LoanEligibility result = customerService.checkEligibility(id,amount);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<Customer> getCustomerDetails(@PathVariable int id) {
        Optional<Customer> customerOptional = customerService.findCustomerById(id);
        return customerOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/customerDetails/{id}") // Corrected typo in endpoint
    public ResponseEntity<CustomerDetails> getCustomerDetailsById(@PathVariable int id) {
        CustomerDetails customerDetails = customerDetailsService.getCustomerDetails(id);
        if (customerDetails != null) {
            return ResponseEntity.ok(customerDetails);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
