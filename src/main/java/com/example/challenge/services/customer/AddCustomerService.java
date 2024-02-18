package com.example.challenge.services.customer;

import org.springframework.stereotype.Service;

import com.example.challenge.entities.Cart;
import com.example.challenge.entities.Customer;
import com.example.challenge.repos.CartRepository;
import com.example.challenge.repos.CustomerRepository;

@Service
public class AddCustomerService {

	private CustomerRepository customerRepository;
    private CartRepository cartRepository;
	public AddCustomerService(CustomerRepository customerRepository, CartRepository cartRepository) {
		super();
		this.customerRepository = customerRepository;
		this.cartRepository = cartRepository;
	}
    
	public Customer addCustomer(Customer customer) {
        // Yeni müşteriyi kaydet
        Customer newCustomer = customerRepository.save(customer);
        
        // Eğer müşteriye ait bir alışveriş sepeti yoksa, yeni bir sepet oluştur
        if (cartRepository.findByCustomerCustomerId(newCustomer.getCustomerId()) == null) {
            Cart newCart = new Cart();
            newCart.setCustomer(newCustomer);
            cartRepository.save(newCart);
        }
        
        return newCustomer;
    }
	
}
