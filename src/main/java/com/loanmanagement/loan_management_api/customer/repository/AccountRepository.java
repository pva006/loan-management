package com.loanmanagement.loan_management_api.customer.repository;

import com.loanmanagement.loan_management_api.customer.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account,Integer> {
}
