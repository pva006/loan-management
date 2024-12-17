package com.loanmanagement.loan_management_api.customer.repository;

import com.loanmanagement.loan_management_api.customer.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository extends JpaRepository<Loan,Integer> {
}
