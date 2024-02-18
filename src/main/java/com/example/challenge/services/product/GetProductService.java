package com.example.challenge.services.product;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.challenge.entities.Product;
import com.example.challenge.repos.ProductRepository;

@Service
public class GetProductService {

	private ProductRepository productRepository;
	
	public GetProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
	
	public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getOneProduct(Long productId) {
        return productRepository.findById(productId).orElse(null);
    }
	
}
