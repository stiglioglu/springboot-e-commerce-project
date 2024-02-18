package com.example.challenge.requests;

import lombok.Data;

@Data
public class CartDeleteRequest {

	Long customerId;
	Long productId;
	int deleteProduct;
	
}
