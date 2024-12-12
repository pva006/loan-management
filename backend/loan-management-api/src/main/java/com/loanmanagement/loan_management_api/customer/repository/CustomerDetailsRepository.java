package com.loanmanagement.loan_management_api.customer.repository;

import com.loanmanagement.loan_management_api.customer.model.CustomerDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerDetailsRepository extends JpaRepository<CustomerDetails,Integer> {
}
