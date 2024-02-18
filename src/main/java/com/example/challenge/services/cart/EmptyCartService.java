package com.example.challenge.services.cart;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.challenge.entities.Cart;
import com.example.challenge.repos.CartRepository;
import com.example.challenge.requests.CartDeleteRequest;
import com.example.challenge.services.SelfService;

@Service
public class EmptyCartService {

	private CartRepository cartRepository;
	private SelfService selfService;
	
	
	
public EmptyCartService(CartRepository cartRepository, SelfService selfService) {
		super();
		this.cartRepository = cartRepository;
		this.selfService = selfService;
	}

public Cart deleteOneCart(CartDeleteRequest cartDeleteRequest) {
		
	    Cart cart = cartRepository.findByCustomerCustomerId(cartDeleteRequest.getCustomerId());
	    if (cart == null) {
	        return null;
	    }
	    
        Map<Long, Integer> products = cart.getProducts();
        Long productId = cartDeleteRequest.getProductId();
        
        if (!products.containsKey(productId)) {
        	return null;
        }
        
        if (cartDeleteRequest.getDeleteProduct()==0) {
        	Integer existingQuantity = products.get(productId);
        	int newQuantity = existingQuantity - 1;
        	if (newQuantity>0) {
        		products.put(productId, newQuantity);
			}
        	else {
        		products.remove(productId);
        	}
		}
        
        if (cartDeleteRequest.getDeleteProduct()==1){
        	products.remove(productId);
        }
        
        cart.setToplamTutar(selfService.getProductTotalPrice(products));
        cartRepository.save(cart);
        
		return cart;
	}
	
	public Cart deleteAllCart(Long customerId) {
		Cart cart = cartRepository.findByCustomerCustomerId(customerId);
	    if (cart == null) {
	        return null;
	    }
	    cart.getProducts().clear();
	    cartRepository.save(cart);
	    return cart;
	}
	
}
