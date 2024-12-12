package com.loanmanagement.loan_management_api.customer.controller;


import com.loanmanagement.loan_management_api.customer.model.Customer;
import com.loanmanagement.loan_management_api.customer.model.CustomerDetails;
import com.loanmanagement.loan_management_api.customer.repository.CustomerRepository;
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

    @GetMapping("/eligibility/{id}")
    public ResponseEntity<String> checkEligibility(@PathVariable int id) {
        String result = customerService.checkEligibility(id);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/details/{id}")
    public ResponseEntity<Customer> getCustomerDetails(@PathVariable int id) {
        Optional<Customer> customerOptional = customerService.findCustomerById(id);
        if (customerOptional.isPresent()) {
            return ResponseEntity.ok(customerOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/cutomerDetails/{id}")
    public ResponseEntity<CustomerDetails> getCustomerDetailsById(@PathVariable int id) {
         CustomerDetails customerOptional = customerDetailsService.getCustomerDetails(id);
            return ResponseEntity.ok(customerOptional);

    }





}










