package com.example.challenge.services.product;

import org.springframework.stereotype.Service;

import com.example.challenge.repos.ProductRepository;

@Service
public class DeleteProductService {

	private ProductRepository productRepository;
	
	public DeleteProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

	public void deleteById(Long productId) {
        productRepository.deleteById(productId);
    }
	
}
