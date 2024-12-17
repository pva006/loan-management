package com.loanmanagement.loan_management_api.customer.service;

import com.loanmanagement.loan_management_api.customer.model.*;
import com.loanmanagement.loan_management_api.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public LoanEligibility checkEligibility(int customerId) {


        LoanEligibility loanEligibility = new LoanEligibility();

        //1. minium Credit score
        //2. OutStanding Loan Limit.
        //3. Account age.
        //4. employment status
        //5. IDR

        boolean isVaildCreditScoure = false;
        boolean isVaildLoanAmount = false ;
        boolean isAccountOld = false;
        boolean isEmployed = false;
        boolean isIdr = false ;

        Optional<Customer> customer = customerRepository.findById(customerId);

        if (customer.isPresent()) {
            Customer c = customer.get();
            CustomerDetails customerDetails = c.getCustomerDetails();

            // Check income and credit score criteria
            if (null != customerDetails &&  customerDetails.getCreditScore() <= 650) {
                isVaildCreditScoure =  true;
            }

            // OutStanding Loan Limit.
            List<Loan> loanList = c.getLoans();

            Double amount = loanList.stream().mapToDouble(Loan::getLoanAmount).sum();

            if (amount >= 10000){
                isVaildLoanAmount = true;
            }

            // 3. Account age
            Account account = c.getAccount();
            LocalDateTime currentDate = LocalDateTime.now();
            LocalDateTime oneYearAgo = currentDate.minusYears(1);

            if (!account.getAccountCreatedDate().isBefore(oneYearAgo)){
                isAccountOld = true;
            }

            //4. Employemnt Satus
            if (!"Employed".equals(c.getEmploymentStatus())){
                isEmployed = true;
            }

            Double yearlyIncome = account.getYearlyIncome();;
            Double balance = account.getBalance();

            Double idr = ( balance/yearlyIncome ) * 100 ;

            if (idr <  40 ) {
                isIdr = true;
            }





            if (isVaildCreditScoure){
                loanEligibility.setLoanStatus("Rejected");
                loanEligibility.setMessage("Customer not eligible for the Loan Low credit Score ");
                return loanEligibility ;
            } else if (isVaildLoanAmount){
                // "Customer not eligible for the Loan becuse of Existing Loan amount grater than 10000 ";
                loanEligibility.setLoanStatus("Rejected");
                loanEligibility.setMessage("Customer not eligible for the Loan Low credit Score ");
                return loanEligibility ;
            } else if (isAccountOld) {
                // "Customer not eligible for the Loan  because of account age is less then one year";

                loanEligibility.setLoanStatus("Rejected");
                loanEligibility.setMessage("Customer not eligible for the Loan Low credit Score ");
                return loanEligibility ;
            } else if (isEmployed){
                // "Customer not eligible for the Loan  because of Customer is not employed";

                loanEligibility.setLoanStatus("Rejected");
                loanEligibility.setMessage("Customer not eligible for the Loan Low credit Score ");
                return loanEligibility ;
            }else if (isIdr){
                // "Customer not eligible for the Loan  because of Customer IDR is Less then 40 % ";

                loanEligibility.setLoanStatus("Rejected");
                loanEligibility.setMessage("Customer not eligible for the Loan Low credit Score ");
                return loanEligibility ;
            }


        }  else {
            // "Customer not found.";

            loanEligibility.setLoanStatus("Rejected");
            loanEligibility.setMessage("Customer not eligible for the Loan Low credit Score ");
            return loanEligibility ;
        }

        // "Customer  eligible for Loan.";

        loanEligibility.setLoanStatus("Rejected");
        loanEligibility.setMessage("Customer not eligible for the Loan Low credit Score ");
        return loanEligibility ;
    }

    public Optional<Customer> findCustomerById(int id) {

        return customerRepository.findById(id);
    }
}
