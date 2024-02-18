package com.example.challenge.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.challenge.entities.Customer;
import com.example.challenge.services.CustomerService;
import com.example.challenge.services.customer.AddCustomerService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/customer")
public class CustomerController {

	CustomerService customerService;
	HttpSession session;
	AddCustomerService addCustomerService;

	public CustomerController(CustomerService customerService, HttpSession session,AddCustomerService addCustomerService) {
		super();
		this.customerService = customerService;
		this.session = session;
		this.addCustomerService = addCustomerService;
	}
	
	@GetMapping
	public String customerGetPage() {
		return "redirect:/";
	}
	
	@GetMapping("/add")
	public String addCustomerGetPage(Model model) {
		if (session.getAttribute("id")!=null) {
			return "redirect:/";
		}
		model.addAttribute("customer",new Customer());
		return "add-customer";
	}
	
	@PostMapping("/add")
	public String addCustomerPostPage(
			@Validated @ModelAttribute("customer") Customer customer) {
		addCustomerService.addCustomer(customer);
		return "redirect:/";
	}
	
	@GetMapping("/login")
	public String loginCustomerGetPage(Model model) {
		if (session.getAttribute("id")!=null) {
			return "redirect:/";
		}
		model.addAttribute("customer",new Customer());
		return "login-customer";
	}
	
	@PostMapping("/login")
	public String loginCustomerPostPage(
			@Validated @ModelAttribute("customer") Customer customer) {
		Customer customer2 = 
				customerService.getNameAndPassword(customer.getCustomerName(), customer.getCustomerPassword());
		if (customer2!=null) {
			session.setAttribute("id", customer2.getCustomerId());
			return "redirect:/";
		}
		return "login-customer";
	}
	
	@GetMapping("/logout")
	public String logoutCustomerGetPage() {
		session.invalidate();
		return "redirect:/";
	}
	
}
