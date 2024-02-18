package com.example.challenge.requests;

import java.util.Map;

import lombok.Data;

@Data
public class CartCreateRequest {

	Long id;
	Long customerId;
	int toplamTutar;
	Map<Long, Integer> productIds;
	
}
