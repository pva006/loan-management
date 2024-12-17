package com.loanmanagement.loan_management_api.customer.service;


import com.loanmanagement.loan_management_api.customer.model.CustomerDetails;
import com.loanmanagement.loan_management_api.customer.repository.CustomerDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerDetailsService {

    @Autowired
    private CustomerDetailsRepository customerDetailsRepository;




    public CustomerDetails getCustomerDetails(int custoerDetaislid){
       return  customerDetailsRepository.findById(custoerDetaislid).get();
    }







}
