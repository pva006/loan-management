package com.loanmanagement.loan_management_api.customer.repository;

import com.loanmanagement.loan_management_api.customer.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {
}
