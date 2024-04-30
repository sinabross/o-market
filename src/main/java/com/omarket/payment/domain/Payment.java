package com.omarket.payment.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
public class Payment {

	private long id;
	private int type;
	private long discountPrice;
	private long totalPrice;
	private boolean status;
	private ZonedDateTime createdAt;

	@Builder
	public Payment(long id, int type, long discountPrice, long totalPrice, boolean status, ZonedDateTime createdAt){
		this.id = id;
		this.type = type;
		this.discountPrice = discountPrice;
		this.totalPrice = totalPrice;
		this.status = status;
		this.createdAt = createdAt;
	}
}
