package com.example.challenge.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.challenge.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
