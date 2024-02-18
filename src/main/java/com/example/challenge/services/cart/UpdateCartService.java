package com.example.challenge.services.cart;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.challenge.entities.Cart;
import com.example.challenge.entities.Product;
import com.example.challenge.repos.CartRepository;
import com.example.challenge.requests.CartUpdateRequest;
import com.example.challenge.services.ProductService;
import com.example.challenge.services.SelfService;

@Service
public class UpdateCartService {

	private CartRepository cartRepository;
	private ProductService productService;
	private SelfService selfService;
	
	
	public UpdateCartService(CartRepository cartRepository, ProductService productService, SelfService selfService) {
		super();
		this.cartRepository = cartRepository;
		this.productService = productService;
		this.selfService = selfService;
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
	
}
