package com.example.challenge.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.challenge.entities.Product;
import com.example.challenge.repos.ProductRepository;

@Service
public class ProductService {

	private ProductRepository productRepository;

	public ProductService(ProductRepository productRepository) {
		super();
		this.productRepository = productRepository;
	}
	
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}
	
	public Product createProduct(Product newProduct) {
		return productRepository.save(newProduct);
	}
	
	public Product getOneProduct(Long productId) {
		//custom exception
		return productRepository.findById(productId).orElse(null);
	}
	
	public Product updateOneProduct(Long productId, Product updateProduct) {
		Optional<Product> product = productRepository.findById(productId);
		if(product.isPresent()) {
			Product foundProduct = product.get();
			foundProduct.setProductName(updateProduct.getProductName());
			foundProduct.setProductPrice(updateProduct.getProductPrice());
			foundProduct.setProductStockQuantity(updateProduct.getProductStockQuantity());
			productRepository.save(foundProduct);
			return foundProduct;
		}else {
			return null;
		}
	}
	
	public void deleteById(Long productId) {
		productRepository.deleteById(productId);
	}
	
}
