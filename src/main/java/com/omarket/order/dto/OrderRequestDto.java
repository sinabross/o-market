package com.omarket.order.dto;

import com.omarket.payment.dto.PaymentType;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.util.Optional;


@Getter
public class OrderRequestDto {

	@NotNull
	private PaymentType type;

	@NotNull
	private String receiverName;

	@NotNull
	private String receiverPhone;

	@Max(50)
	@NotNull
	private String receiverRequest;

	private Optional<Long> couponId;

	@Builder
	public OrderRequestDto(PaymentType type, String receiverName, String receiverPhone, String receiverRequest, Optional<Long> couponId){
		this.type = type;
		this.receiverName = receiverName;
		this.receiverPhone = receiverPhone;
		this.receiverRequest = receiverRequest;
		this.couponId = couponId;
	}
}
