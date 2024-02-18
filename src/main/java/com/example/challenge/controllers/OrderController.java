package com.example.challenge.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.challenge.entities.Cart;
import com.example.challenge.responses.OrderDetailResponse;
import com.example.challenge.services.CartService;
import com.example.challenge.services.OrderService;
import com.example.challenge.services.SelfService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/order")
public class OrderController {

	OrderService orderService;
	CartService cartService;
	HttpSession session;
	SelfService selfService;
	
	public OrderController(OrderService orderService, CartService cartService, HttpSession session,
			SelfService selfService) {
		super();
		this.orderService = orderService;
		this.cartService = cartService;
		this.session = session;
		this.selfService = selfService;
	}
	
	@GetMapping
	public String orderGetPage(Model model) {
		Long customerId = (Long) session.getAttribute("id");
		model.addAttribute("session",session);
		if (session==null) {
			return "order";
		}
		
		List<OrderDetailResponse> orderDetails = orderService.getOrderDetailById(customerId);
		model.addAttribute("orders",orderDetails);
		
		return "order";
	}
	
	
	@PostMapping("/siparis")
	public String cartSiparisVerPostPage(RedirectAttributes redirectAttributes) {
		Long customerId = (Long) session.getAttribute("id");
		
		if (customerId==null) {
			return "redirect:/cart";
		}
		
		Cart cart = cartService.getOneCart(customerId);
		Map<Long, Integer> productMap = cart.getProducts();
		
		if (productMap.isEmpty()) {
			redirectAttributes.addFlashAttribute(
					"error","Sepetinizde ürün bulunmuyor");
			return "redirect:/cart";
		}
		
		String checkProductStock = selfService.checkProductStock(productMap);
		if (checkProductStock!=null) {
			redirectAttributes.addFlashAttribute(
					"error",checkProductStock+" ürününden yeterli sayıda stok elimizde bulunmuyor");
		}
		else {
			orderService.createOrder(customerId,productMap);
		}
		
		return "redirect:/cart";
	}
	
}
