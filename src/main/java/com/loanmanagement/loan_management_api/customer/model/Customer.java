package com.loanmanagement.loan_management_api.customer.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "customer_test" )
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Integer customerId;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email")
    private String email;
    @Column(name = "address_id")
    private int addressId;
    @Column(name = "create_date")
    private LocalDateTime createDate;
    @Column(name = "last_update")
    private LocalDateTime lastUpdate;
    @Column(name = "active")
    private int active;
    @Column(name = "store_id")
    private int storeId;
    @Column(name = "employment_status")
    private String employmentStatus;

    @OneToOne(mappedBy = "customer" ,cascade = CascadeType.ALL , orphanRemoval = true)
    @JsonManagedReference
    private CustomerDetails customerDetails;

    @OneToOne
    @JoinColumn(name = "account_id" )
    @JsonManagedReference
    private Account account;

    @OneToMany(mappedBy = "customer")
    @JsonManagedReference
    private List<Loan> loans;


}
