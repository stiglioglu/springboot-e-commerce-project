package com.example.challenge.controllers.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.challenge.entities.Cart;
import com.example.challenge.requests.CartCreateRequest;
import com.example.challenge.requests.CartDeleteRequest;
import com.example.challenge.requests.CartUpdateRequest;
import com.example.challenge.services.CartService;

@RestController
@RequestMapping("/api/cart")
public class CartApiController {
	
	CartService cartService;

	public CartApiController(CartService cartService) {
		super();
		this.cartService = cartService;
	}
	
	@GetMapping
	public List<Cart> getAllCarts() {
		return cartService.getAllCarts();
	}
	
	@PostMapping
	public Cart createCart(@RequestBody CartCreateRequest newCartRequest) {
		return cartService.createCart(newCartRequest);
	}
	
	@GetMapping("/{customerId}")
	public Cart getOneCart(@PathVariable Long customerId) {
		System.out.println(customerId);
		return cartService.getOneCart(customerId);
	}
	
	@PostMapping("/update")
	public Cart updateOneCart(@RequestBody CartUpdateRequest cartUpdateRequest) {
		return cartService.updateOneCart(cartUpdateRequest);
	}
	
	@PostMapping("/delete")
	public Cart deleteOneCart(@RequestBody CartDeleteRequest cartDeleteRequest) {
		return cartService.deleteOneCart(cartDeleteRequest);
	}
	
}
