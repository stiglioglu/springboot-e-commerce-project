package com.example.challenge.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.challenge.entities.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

	Cart findByCustomerCustomerId(Long customerId);

}
