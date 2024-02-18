package com.example.challenge.controllers.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.challenge.entities.Product;
import com.example.challenge.services.product.CreateProductService;
import com.example.challenge.services.product.DeleteProductService;
import com.example.challenge.services.product.GetProductService;
import com.example.challenge.services.product.UpdateProductService;

@RestController
@RequestMapping("/api/product")
public class ProductApiController {

	CreateProductService createProductService;
	DeleteProductService deleteProductService;
	GetProductService getProductService;
	UpdateProductService updateProductService;
	public ProductApiController(CreateProductService createProductService, DeleteProductService deleteProductService,
			GetProductService getProductService, UpdateProductService updateProductService) {
		super();
		this.createProductService = createProductService;
		this.deleteProductService = deleteProductService;
		this.getProductService = getProductService;
		this.updateProductService = updateProductService;
	}
	
	
	@GetMapping
	public List<Product> getAllProducts() {
		return getProductService.getAllProducts();
	}
	
	@PostMapping
	public Product createProduct(@RequestBody Product newProduct) {
		return createProductService.createProduct(newProduct);
	}
	
	@GetMapping("/{productId}")
	public Product getOneProduct(@PathVariable Long productId) {
		//custom exception
		return getProductService.getOneProduct(productId);
	}
	
	@PutMapping("/{productId}")
	public Product updateOneProduct(@PathVariable Long productId, @RequestBody Product updateProduct) {
		return updateProductService.updateOneProduct(productId, updateProduct);
	}
	
}
