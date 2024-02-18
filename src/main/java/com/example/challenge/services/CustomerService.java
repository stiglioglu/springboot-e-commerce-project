package com.example.challenge.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.challenge.entities.Cart;
import com.example.challenge.entities.Customer;
import com.example.challenge.repos.CartRepository;
import com.example.challenge.repos.CustomerRepository;

@Service
public class CustomerService {

	CustomerRepository customerRepository;
	CartRepository cartRepository;

	public CustomerService(CustomerRepository customerRepository,CartRepository cartRepository) {
		super();
		this.customerRepository = customerRepository;
		this.cartRepository = cartRepository;
	}
	
	public List<Customer> getAllCustomers(){
		return customerRepository.findAll();
	}
	
	public Customer createCustomer(Customer customer) {
		Customer newCustomer = customerRepository.save(customer);
		Cart newCart = cartRepository.findByCustomerCustomerId(newCustomer.getCustomerId());
		if (newCart==null) {
			Cart toSaveCart = new Cart();
		    toSaveCart.setCustomer(newCustomer);
		    cartRepository.save(toSaveCart);
		}
		return newCustomer;
	}
	
	public Customer getOneCustomer(Long userId) {
		return customerRepository.findById(userId).orElse(null);
	}
	
	public Customer getNameAndPassword(String userName, String userPass) {
		return customerRepository.findByCustomerNameAndCustomerPassword(userName, userPass);
	}
	
}
