package com.loanmanagement.loan_management_api.customer.service;

import com.loanmanagement.loan_management_api.customer.model.Customer;
import com.loanmanagement.loan_management_api.customer.model.CustomerDetails;
import com.loanmanagement.loan_management_api.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public String checkEligibility(int id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()) {
            Customer c = customer.get();
            CustomerDetails customerDetails = c.getCustomerDetails();

            // Check income and credit score criteria
            if (null != customerDetails && customerDetails.getIncome() >= 6000 && customerDetails.getCreditScore() >= 800) {
                return "Customer is eligible for the loan.";
            } else {
                return "Customer is not eligible for the loan. Insufficient income or low credit score.";
            }
        }

        return "Customer not found.";
    }

    public Optional<Customer> findCustomerById(int id) {

        return customerRepository.findById(id);
    }
}
