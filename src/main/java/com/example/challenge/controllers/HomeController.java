package com.example.challenge.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.challenge.entities.Product;
import com.example.challenge.services.ProductService;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

	private ProductService productService;
	HttpSession session;
	
	public HomeController(ProductService productService, HttpSession session) {
		super();
		this.productService = productService;
		this.session = session;
	}
	
	@GetMapping("/")
	public String homePage(Model model) {
		List<Product> products = productService.getAllProducts();
		model.addAttribute("products", products);
		model.addAttribute("session",session);
		return "index";
	}
	
	@GetMapping("/addProduct")
	public String addProductGetPage(Model model) {
		model.addAttribute("product",new Product());
		return "add-product";
	}
	
	@PostMapping("/addProduct")
	public String addProductPostPage(
			@Validated @ModelAttribute("product") Product product) {
		productService.createProduct(product);
		return "redirect:/";
	}
	
	@GetMapping("/product")
	public String productsGetPage(Model model) {
		List<Product> products = productService.getAllProducts();
		model.addAttribute("products", products);
		return "list-product";
	}
	
}
