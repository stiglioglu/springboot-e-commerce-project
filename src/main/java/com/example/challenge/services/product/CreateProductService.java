package com.example.challenge.services.product;

import org.springframework.stereotype.Service;

import com.example.challenge.entities.Product;
import com.example.challenge.repos.ProductRepository;

@Service
public class CreateProductService {

	private ProductRepository productRepository;
	
	public CreateProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(Product newProduct) {
        return productRepository.save(newProduct);
    }
	
}
