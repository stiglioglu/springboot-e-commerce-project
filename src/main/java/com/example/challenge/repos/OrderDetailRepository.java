package com.example.challenge.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.challenge.entities.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

	List<OrderDetail> findByOrderOrderId(Long orderId);

}
