package com.example.challenge.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.challenge.entities.Product;
import com.example.challenge.services.product.CreateProductService;
import com.example.challenge.services.product.DeleteProductService;
import com.example.challenge.services.product.GetProductService;
import com.example.challenge.services.product.UpdateProductService;


@Controller
@RequestMapping("/products")
public class ProductController {
	
	private CreateProductService createProductService;
	private DeleteProductService deleteProductService;
	private GetProductService getProductService;
	private UpdateProductService updateProductService;
	
	public ProductController(CreateProductService createProductService,
			DeleteProductService deleteProductService, GetProductService getProductService,
			UpdateProductService updateProductService) {
		super();
		this.createProductService = createProductService;
		this.deleteProductService = deleteProductService;
		this.getProductService = getProductService;
		this.updateProductService = updateProductService;
	}
	
	@GetMapping
	public String productsGetPage(Model model) {
		List<Product> products = getProductService.getAllProducts();
		
		model.addAttribute("products", products);
		return "list-product";
	}
	
	@GetMapping("/addProduct")
	public String addProductGetPage(Model model) {
		model.addAttribute("product",new Product());
		return "add-product";
	}
	
	@PostMapping("/addProduct")
	public String addProductPostPage(
			@Validated @ModelAttribute("product") Product product) {
		createProductService.createProduct(product);
		
		return "redirect:/products";
	}
	
	@PostMapping("/update")
	public String addProductPostPage(
			@RequestParam Long productId,
			@Validated @ModelAttribute("product") Product product) {
		updateProductService.updateOneProduct(productId, product);
		
		return "redirect:/products";
	}
	
	@GetMapping("/update/{productId}")
	public String productsOneUpdate(@PathVariable Long productId, Model model) {
		Product product = getProductService.getOneProduct(productId);
		
		model.addAttribute("product", product);
		return "update-product";
	}
	
	@GetMapping("/delete/{productId}")
	public String deleteById(@PathVariable Long productId) {
		deleteProductService.deleteById(productId);
		
		return "redirect:/products";
	}
	
	
}
