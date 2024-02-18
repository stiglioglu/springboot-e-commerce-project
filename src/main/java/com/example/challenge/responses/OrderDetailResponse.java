package com.example.challenge.responses;


import java.util.Date;

import lombok.Data;

@Data
public class OrderDetailResponse {

	String isim;
	double fiyat;
	double guncelFiyat;
	int adet;
	Date tarih;
	
}
