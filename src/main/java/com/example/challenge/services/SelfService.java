package com.example.challenge.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.challenge.entities.Product;
import com.example.challenge.repos.ProductRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class SelfService {

	ProductService productService;
	HttpSession session;
	ProductRepository productRepository;
	
	
	public SelfService(ProductService productService,HttpSession session,ProductRepository productRepository) {
		super();
		this.productService = productService;
		this.session = session;
		this.productRepository = productRepository;
	}

	public List<Product> getProductListByMap(Map<Long, Integer> productsMap){
		List<Product> productList = new ArrayList<Product>();
        for (Map.Entry<Long, Integer> entry : productsMap.entrySet()) {
        	Product product = productService.getOneProduct(entry.getKey());
        	if (product==null) {
				continue;
			}
            productList.add(product);
        }
        return productList;
	}
	
	public Double getProductTotalPrice(Map<Long, Integer> productsMap) {
		List<Product> productsList = getProductListByMap(productsMap);
		if (productsList==null || productsMap==null) {
			return 0.0;
		}
		double totalPrice = 0;
		for (Product x : productsList) {
			totalPrice += x.getProductPrice() * productsMap.get(x.getProductId());
        }
		return totalPrice;
	}
	
	public Double getProductTotalPrice(Map<Long, Integer> productsMap, List<Product> productsList) {
		if (productsList==null || productsMap == null) {
			return 0.0;
		}
		double totalPrice = 0;
		for (Product x : productsList) {
			totalPrice += x.getProductPrice() * productsMap.get(x.getProductId());
        }
		return totalPrice;
	}
	
	public String checkProductStock(Map<Long, Integer> productMap) {
		for (Map.Entry<Long, Integer> entry : productMap.entrySet()) {
		    Long key = entry.getKey();
		    Integer value = entry.getValue();
		    Product product = productService.getOneProduct(key);
		    if (product==null) {
				continue;
			}
		    if (value <= product.getProductStockQuantity()) {
				continue;
		    }
		    else {
		    	return product.getProductName();
		    }
		}
		return null;
	}
	
	public double checkCurrentPriceProduct(Long productId) {
		Product product = productRepository.findById(productId).orElse(null);
		if (product!=null) {
			return product.getProductPrice();
		}
		return 0.0;
	}
	
}
