package com.loadmanagement;

import com.loanmanagement.loan_management_api.customer.model.*;
import com.loanmanagement.loan_management_api.customer.repository.CustomerRepository;
import com.loanmanagement.loan_management_api.customer.repository.LoanApplicationHistoryRepository;
import com.loanmanagement.loan_management_api.customer.service.CustomerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class LoanEligibilityServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private LoanApplicationHistoryRepository loanApplicationHistoryRepository;

    @InjectMocks
    private CustomerService customerService;


    @Test
    public void testCheckEligibility_CustomerNotFound(){

        int customerId = 113246;
        double requestedAmount = 5000;
        Mockito.when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        LoanEligibility result = customerService.checkEligibility(customerId,requestedAmount);

        Assertions.assertEquals("Rejected",result.getLoanStatus());
        Assertions.assertEquals("Customer not found",result.getMessage());

    }


    @Test
    public void testCheckEligibility_LowCreditScore(){

        int customerId = 1;
        double requestedAmount = 5000;
         Customer customer = createMockCutomer(100,15269,LocalDateTime.now(),"Employed",1542,1458789632);
        Mockito.when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        LoanEligibility result = customerService.checkEligibility(customerId,requestedAmount);

        Assertions.assertEquals("Rejected",result.getLoanStatus());
        Assertions.assertEquals("Customer not eligible due to low credit score .",result.getMessage());

    }



    @Test
    public void testCheckEligibility_exceededAmount(){

        int customerId = 1;
        double requestedAmount = 5000;
        Customer customer = createMockCutomer(700,154548,LocalDateTime.now(),"Employed",1542,1458789632);
        Mockito.when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        LoanEligibility result = customerService.checkEligibility(customerId,requestedAmount);

        Assertions.assertEquals("Rejected",result.getLoanStatus());
        Assertions.assertEquals("Existing loan amount exceeds $10000",result.getMessage());

    }


    @Test
    public void testCheckEligibility_age(){

        int customerId = 1;
        double requestedAmount = 5000;
        Customer customer = createMockCutomer(700,1234,LocalDateTime.now().minusMonths(2),"Employed",1542,1458789632);
        Mockito.when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        LoanEligibility result = customerService.checkEligibility(customerId,requestedAmount);

        Assertions.assertEquals("Rejected",result.getLoanStatus());
        Assertions.assertEquals("Account age is less than one year",result.getMessage());

    }

    @Test
    public void testCheckEligibility_EmoplyedStatus(){

        int customerId = 1;
        double requestedAmount = 5000;
        Customer customer = createMockCutomer(700,1234,LocalDateTime.now().minusYears(2),"Un-Employed",1542,1458789632);
        Mockito.when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        LoanEligibility result = customerService.checkEligibility(customerId,requestedAmount);

        Assertions.assertEquals("Rejected",result.getLoanStatus());
        Assertions.assertEquals("Customer is not employed",result.getMessage());

    }


    @Test
    public void testCheckEligibility_IDR(){

        int customerId = 1;
        double requestedAmount = 5000;
        Customer customer = createMockCutomer(700,1234,LocalDateTime.now().minusYears(2),"Employed",1542,1458789632);
        Mockito.when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        LoanEligibility result = customerService.checkEligibility(customerId,requestedAmount);

        Assertions.assertEquals("Rejected",result.getLoanStatus());
        Assertions.assertEquals("Income-to-Debt Ratio is below 40%",result.getMessage());

    }



    @Test
    public void testCheckEligibility_approved(){

        int customerId = 1;
        double requestedAmount = 5000;
        Customer customer = createMockCutomer(700,1234,LocalDateTime.now().minusYears(2),"Employed",1542222222,1458789632);
        Mockito.when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        LoanEligibility result = customerService.checkEligibility(customerId,requestedAmount);

        Assertions.assertEquals("Approved",result.getLoanStatus());

    }



    private Customer createMockCutomer(int creditScore, double loanAmount, LocalDateTime accountCreatedDate, String employmentStatus, double balance,double yearlyIncome){

        CustomerDetails customerDetails = new CustomerDetails();
        customerDetails.setCreditScore(creditScore);

        Loan loan = new Loan();
        loan.setLoanAmount(loanAmount);


        Account account = new Account();
        account.setAccountCreatedDate(accountCreatedDate);
        account.setBalance(balance);
account.setYearlyIncome(yearlyIncome);

Customer customer = new Customer();
customer.setCustomerId(2);
customer.setLoans(List.of(loan));
customer.setAccount(account);
customer.setEmploymentStatus(employmentStatus);
customer.setCustomerDetails(customerDetails);

return  customer;
    }


}
