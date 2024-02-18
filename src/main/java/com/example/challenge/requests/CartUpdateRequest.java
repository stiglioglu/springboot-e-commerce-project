package com.example.challenge.requests;

import lombok.Data;

@Data
public class CartUpdateRequest {
	
	Long customerId;
	Long productId;

}
