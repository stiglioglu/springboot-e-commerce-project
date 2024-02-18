package com.example.challenge.services.cart;

import org.springframework.stereotype.Service;

import com.example.challenge.entities.Cart;
import com.example.challenge.repos.CartRepository;

@Service
public class GetCartService {

	private CartRepository cartRepository;

	public GetCartService(CartRepository cartRepository) {
		super();
		this.cartRepository = cartRepository;
	}
	
	public Cart getOneCart(Long customerId) {
		return cartRepository.findByCustomerCustomerId(customerId);
	}
	
}
