package com.omarket.order.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
public class Order {

	private long id;
	private long userId;
	private String consumerName;
	private String consumerPhone;
	private String receiverName;
	private String receiverAddress;
	private String receiverPhone;
	private String receiverRequest;
	private boolean status;
	private ZonedDateTime createdAt;
	private long paymentId;

	@Builder
	public Order(long id, long userId, String consumerName, String consumerPhone, String receiverName, String receiverAddress, String receiverPhone, String receiverRequest, boolean status, ZonedDateTime createdAt, long paymentId){
		this.id = id;
		this.userId = userId;
		this.consumerName = consumerName;
		this.consumerPhone = consumerPhone;
		this.receiverName = receiverName;
		this.receiverAddress = receiverAddress;
		this.receiverPhone = receiverPhone;
		this.receiverRequest = receiverRequest;
		this.status = status;
		this.createdAt = createdAt;
		this.paymentId = paymentId;
	}
}