package com.example.challenge.services.product;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.challenge.entities.Product;
import com.example.challenge.repos.ProductRepository;

@Service
public class UpdateProductService {

	private ProductRepository productRepository;
	
	public UpdateProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
	
	public Product updateOneProduct(Long productId, Product updateProduct) {
        Optional<Product> product = productRepository.findById(productId);
        if (product.isPresent()) {
            Product foundProduct = product.get();
            foundProduct.setProductName(updateProduct.getProductName());
            foundProduct.setProductPrice(updateProduct.getProductPrice());
            foundProduct.setProductStockQuantity(updateProduct.getProductStockQuantity());
            return productRepository.save(foundProduct);
        } else {
            return null;
        }
    }
	
}
