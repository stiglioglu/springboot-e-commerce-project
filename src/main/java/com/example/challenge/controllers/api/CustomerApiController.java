package com.example.challenge.controllers.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.challenge.entities.Customer;
import com.example.challenge.services.CustomerService;

@RestController
@RequestMapping("/api/customer")
public class CustomerApiController {

	CustomerService customerService;

	public CustomerApiController(CustomerService customerService) {
		super();
		this.customerService = customerService;
	}
	
	@GetMapping
	public List<Customer> getAllCustomers(){
		return customerService.getAllCustomers();
	}
	
	@PostMapping
	public Customer createCustomer(@RequestBody Customer newCustomer) {
		return customerService.createCustomer(newCustomer);
	}
	
	@GetMapping("/{userId}")
	public Customer getOneCustomer(@PathVariable Long userId) {
		return customerService.getOneCustomer(userId);
	}
	
	@GetMapping("/{userName}/{userPass}")
	public Customer getNameAndPassword(@PathVariable String userName, @PathVariable String userPass) {
		return customerService.getNameAndPassword(userName, userPass);
	}
	
}
