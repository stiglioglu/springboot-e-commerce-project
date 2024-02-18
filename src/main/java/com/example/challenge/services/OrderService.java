package com.example.challenge.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.challenge.entities.Customer;
import com.example.challenge.entities.Order;
import com.example.challenge.entities.OrderDetail;
import com.example.challenge.entities.Product;
import com.example.challenge.repos.CustomerRepository;
import com.example.challenge.repos.OrderDetailRepository;
import com.example.challenge.repos.OrderRepository;
import com.example.challenge.repos.ProductRepository;
import com.example.challenge.responses.OrderDetailResponse;

@Service
public class OrderService {

	OrderRepository orderRepository;
	OrderDetailRepository orderDetailRepository;
	CustomerRepository customerRepository;
	ProductRepository productRepository;
	CartService cartService;
	SelfService selfService;

	public OrderService(
			OrderRepository orderRepository, 
			OrderDetailRepository orderDetailRepository,
			CustomerRepository customerRepository,
			ProductRepository productRepository,
			CartService cartService,
			SelfService selfService) {
		super();
		this.orderRepository = orderRepository;
		this.orderDetailRepository = orderDetailRepository;
		this.customerRepository = customerRepository;
		this.productRepository = productRepository;
		this.cartService = cartService;
		this.selfService = selfService;
	}
	
	public List<Order> getAllOrders(){
		return orderRepository.findAll();
	}
	
	
	public List<OrderDetailResponse> getOrderDetailById(Long customerId){
		List<Order> orders = orderRepository.findByCustomerCustomerId(customerId);
		if (orders==null) {
			return null;
		}
		
		List<OrderDetailResponse> orderDetailResponses = new ArrayList<OrderDetailResponse>();
		
		for(Order order : orders) {
	        List<OrderDetail> details = orderDetailRepository.findByOrderOrderId(order.getOrderId());
	        if (details == null) {
				continue;
			}
	        for(OrderDetail detail:details) {
	        	OrderDetailResponse orderDetailResponse = new OrderDetailResponse();
	        	orderDetailResponse.setIsim(detail.getIsim());
	        	orderDetailResponse.setAdet(detail.getAdet());
	        	orderDetailResponse.setFiyat(detail.getFiyat());
	        	orderDetailResponse.setTarih(order.getCreatedAt());
	        	orderDetailResponse.setGuncelFiyat(selfService.checkCurrentPriceProduct(detail.getProductId()));
	        	orderDetailResponses.add(orderDetailResponse);
	        }
	    }
		return orderDetailResponses;
	}
	
	
	public Order createOrder(Long customerId, Map<Long, Integer> productMap) {
		Customer customer = customerRepository.findById(customerId).orElse(null);
		if (customer==null) {
			return null;
		}
		Order order = new Order();
		order.setCustomer(customer);
		orderRepository.save(order);
		
		for (Map.Entry<Long, Integer> entry : productMap.entrySet()) {
		    Long productId = entry.getKey();
		    Integer quantity = entry.getValue();
		    Product product = productRepository.findById(productId).orElse(null);
		    if (product==null) {
				return null;
			}
		    
		    int stock = product.getProductStockQuantity();
		    int newStok = stock-quantity;
		    if (newStok<0) {
				newStok=0;
			}
		    product.setProductStockQuantity(newStok);
		    
		    OrderDetail orderDetail = new OrderDetail();
			orderDetail.setOrder(order);
			orderDetail.setProductId(product.getProductId());
			orderDetail.setIsim(product.getProductName());
			orderDetail.setFiyat(product.getProductPrice());
			orderDetail.setAdet(quantity);
			
			
			
			productRepository.save(product);
			orderDetailRepository.save(orderDetail);
		}
		
		cartService.deleteAllCart(customerId);
		
		return order;
	}
	
}
