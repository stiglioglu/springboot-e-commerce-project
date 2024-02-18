package com.example.challenge.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.challenge.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

	Customer findByCustomerNameAndCustomerPassword(String customerName, String customerPassword);

}
