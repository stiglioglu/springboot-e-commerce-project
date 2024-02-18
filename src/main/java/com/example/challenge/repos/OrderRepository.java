package com.example.challenge.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.challenge.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

	List<Order> findByCustomerCustomerId(Long customerId);

}
