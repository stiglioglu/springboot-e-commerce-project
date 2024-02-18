package com.example.challenge.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.challenge.entities.Cart;
import com.example.challenge.entities.Product;
import com.example.challenge.requests.CartDeleteRequest;
import com.example.challenge.requests.CartUpdateRequest;
import com.example.challenge.services.CartService;
import com.example.challenge.services.SelfService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/cart")
public class CartController {
	
	CartService cartService;
	HttpSession session;
	SelfService selfService;

	public CartController(CartService cartService, HttpSession session,SelfService selfService) {
		super();
		this.cartService = cartService;
		this.session = session;
		this.selfService = selfService;
	}
	
	@GetMapping
	public String cartGetPage(Model model) {
		Long customerId = (Long) session.getAttribute("id");
		model.addAttribute("session",session);
		if (customerId==null) {
			return "cart";
		}
		Cart cart = cartService.getOneCart(customerId);
		cartService.updateToplamTutar(cart);
		
		List<Product> products = selfService.getProductListByMap(cart.getProducts());
		Map<Long, Integer> productMap = cart.getProducts();
		
		model.addAttribute("cart", cart);
		model.addAttribute("products", products);
		model.addAttribute("productMap", productMap);
		return "cart";
	}
	
	@PostMapping("/arttır")
	public String cartArttırPostPage(@RequestParam Long customerId, @RequestParam Long productId) {
		if (customerId!=null && productId!=null) {
			CartUpdateRequest cartUpdateRequest = new CartUpdateRequest();
			cartUpdateRequest.setCustomerId(customerId);
			cartUpdateRequest.setProductId(productId);
			cartService.updateOneCart(cartUpdateRequest);
		}
		return "redirect:/cart";
	}
	
	@PostMapping("/ekle")
	public String cartEklePostPage(@RequestParam Long customerId, @RequestParam Long productId) {
		if (customerId!=null && productId!=null) {
			CartUpdateRequest cartUpdateRequest = new CartUpdateRequest();
			cartUpdateRequest.setCustomerId(customerId);
			cartUpdateRequest.setProductId(productId);
			cartService.updateOneCart(cartUpdateRequest);
		}
		return "redirect:/cart";
	}
	
	@PostMapping("/azalt")
	public String cartAzaltPostPage(@RequestParam Long customerId, @RequestParam Long productId) {
		CartDeleteRequest cartDeleteRequest = new CartDeleteRequest();
		cartDeleteRequest.setCustomerId(customerId);
		cartDeleteRequest.setProductId(productId);
		cartDeleteRequest.setDeleteProduct(0);
		cartService.deleteOneCart(cartDeleteRequest);
		return "redirect:/cart";
	}
	
	@PostMapping("/sil")
	public String cartSilPostPage(@RequestParam Long customerId, @RequestParam Long productId) {
		CartDeleteRequest cartDeleteRequest = new CartDeleteRequest();
		cartDeleteRequest.setCustomerId(customerId);
		cartDeleteRequest.setProductId(productId);
		cartDeleteRequest.setDeleteProduct(1);
		cartService.deleteOneCart(cartDeleteRequest);
		return "redirect:/cart";
	}
	
	
	
}
