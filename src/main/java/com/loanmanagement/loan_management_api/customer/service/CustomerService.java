package com.loanmanagement.loan_management_api.customer.service;

import com.loanmanagement.loan_management_api.customer.model.*;
import com.loanmanagement.loan_management_api.customer.repository.CustomerRepository;
import com.loanmanagement.loan_management_api.customer.repository.LoanApplicationHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private LoanApplicationHistoryRepository loanApplicationHistoryRepository;

    public LoanEligibility checkEligibility(int customerId, double requestedLoanAmount){

        Optional<Customer> customerOptional = customerRepository.findById(customerId);

        if (customerOptional.isEmpty()){
            return  setLoanStatus("Rejected","Customer not found");
        }

        Customer customer = customerOptional.get();
        CustomerDetails customerDetails = customer.getCustomerDetails();

        if(isInvalidCreditScore(customerDetails)){
            createLoanHistory(customerId,requestedLoanAmount,"Rejected");
            return setLoanStatus("Rejected","Customer not eligible due to low credit score .");
        }


        if (exceedsLoanAmount(customer)) {
            createLoanHistory(customerId,requestedLoanAmount,"Rejected");
            return setLoanStatus("Rejected","Existing loan amount exceeds $10000");
        }


        if (isNewAccount(customer.getAccount())){
            createLoanHistory(customerId,requestedLoanAmount,"Rejected");
            return setLoanStatus("Rejected","Account age is less than one year");
        }

        if(!isEmployed(customer)){
            createLoanHistory(customerId,requestedLoanAmount,"Rejected");
            return setLoanStatus("Rejected","Customer is not employed");
        }

       if( isInvalidIncomeDebtRatio(customer.getAccount()) ) {
           createLoanHistory(customerId,requestedLoanAmount,"Rejected");
           return setLoanStatus("Rejected","Income-to-Debt Ratio is below 40%");
       }

        createLoanHistory(customerId,requestedLoanAmount,"Approved");

        return setLoanStatus("Approved","Customer is eligible for the Loan");
    }

    public Optional<Customer> findCustomerById(int id) {
        return customerRepository.findById(id);
    }

    private void createLoanHistory(int customerId, double amount,String status) {
        LoanApplicationHistory history = new LoanApplicationHistory(customerId,amount,LocalDateTime.now(),status);
        loanApplicationHistoryRepository.save(history);
    }


    private LoanEligibility setLoanStatus (String status,String message) {
        LoanEligibility loanEligibility = new LoanEligibility();
        loanEligibility.setMessage(message);
        loanEligibility.setLoanStatus(status);
        return loanEligibility;
    }

    // 1. minimum credit score
    private boolean isInvalidCreditScore(CustomerDetails details){
        return  details == null || details.getCreditScore() <= 650;
    }


    // 2. exceeds Current Loan amount is $10000
    private boolean exceedsLoanAmount (Customer customer) {
        Double amount = customer.getLoans().stream().mapToDouble(Loan::getLoanAmount).sum();
        return  amount >=10000;
    }

    //3. Less than a year account created
    private boolean isNewAccount(Account account){
        return account.getAccountCreatedDate().isAfter(LocalDateTime.now().minusYears(1));
    }

    //4. Employeed Status
    private boolean isEmployed(Customer customer){
        return "Employed".equals(customer.getEmploymentStatus());
    }


    // 5 IDR value
    private boolean isInvalidIncomeDebtRatio(Account account) {
        double idr = (account.getBalance() / account.getYearlyIncome() ) * 100 ;
        return  idr <=40;
    }
}
