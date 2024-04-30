package com.omarket.payment.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class PaymentDto {

	@NotNull
	private PaymentType type;

	private long discountPrice;

	@NotNull
	private long totalPrice;

	@Builder
	public PaymentDto(PaymentType type, long discountPrice, long totalPrice){
		this.type = type;
		this.discountPrice = discountPrice;
		this.totalPrice = totalPrice;
	}
}
