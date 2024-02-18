package com.example.challenge.services;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.challenge.entities.Cart;
import com.example.challenge.entities.Customer;
import com.example.challenge.entities.Product;
import com.example.challenge.repos.CartRepository;
import com.example.challenge.requests.CartCreateRequest;
import com.example.challenge.requests.CartDeleteRequest;
import com.example.challenge.requests.CartUpdateRequest;

@Service
public class CartService {

	CartRepository cartRepository;
	CustomerService customerService;
	ProductService productService;
	SelfService selfService;

	public CartService(
			CartRepository cartRepository, 
			CustomerService customerService, 
			ProductService productService,
			SelfService selfService
	) {
		super();
		this.cartRepository = cartRepository;
		this.customerService = customerService;
		this.productService = productService;
		this.selfService = selfService;
	}
	
	public List<Cart> getAllCarts() {
		return cartRepository.findAll();
	}
	
	public Cart createCart(CartCreateRequest newCartRequest) {
	    Customer customer = customerService.getOneCustomer(newCartRequest.getCustomerId());
	    if (customer == null) {
	        return null;
	    }
	    Cart toSaveCart = new Cart();
	    toSaveCart.setCustomer(customer);
	    toSaveCart.setProducts(newCartRequest.getProductIds());
	    toSaveCart.setToplamTutar(newCartRequest.getToplamTutar());
	    return cartRepository.save(toSaveCart);
	}
	
	public Cart getOneCart(Long customerId) {
		return cartRepository.findByCustomerCustomerId(customerId);
	}

	public Cart updateOneCart(CartUpdateRequest cartUpdateRequest) {
	    // İlgili ürünü al
	    Product product = productService.getOneProduct(cartUpdateRequest.getProductId());
	    if (product == null) {
	        return null; // Eğer ürün bulunamazsa null döndür
	    }
	    
	    // İlgili müşteriye ait sepeti al
	    Cart cart = cartRepository.findByCustomerCustomerId(cartUpdateRequest.getCustomerId());
	    if (cart == null) {
	        return null; // Eğer sepet bulunamazsa null döndür
	    } 
	    
        // Sepetteki ürünleri al
        Map<Long, Integer> products = cart.getProducts();
        Long productId = cartUpdateRequest.getProductId();
        
        // Eğer sepet zaten ilgili ürünü içeriyorsa, miktarını artır
        if (products.containsKey(productId)) {
            Integer existingQuantity = products.get(productId);
            int newQuantity = existingQuantity + 1;
            // Yeni miktar stok miktarından fazla değilse, güncelle
            if (newQuantity <= product.getProductStockQuantity()) {
                products.put(productId, newQuantity);
            }
        } else {
            // Sepette ilgili ürün yoksa, yeni bir giriş oluştur
            products.put(productId, 1);
        }
        
        // Sepeti güncelle
        cart.setProducts(products);
        cart.setToplamTutar(selfService.getProductTotalPrice(products));
        cartRepository.save(cart);
        
        return cart; // Güncellenmiş sepeti döndür
	    
	}
	
	public void updateToplamTutar(Cart cart) {
		Map<Long, Integer> products = cart.getProducts();
		cart.setToplamTutar(selfService.getProductTotalPrice(products));
		cartRepository.save(cart);
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
