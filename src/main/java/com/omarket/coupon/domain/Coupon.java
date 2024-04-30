package com.omarket.coupon.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Getter
public class Coupon {

	private Long id;

	private String name;

	private Long minPrice;

	private Long discountPrice;

	private Long productId;

	private int maxCouponCount;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private ZonedDateTime expirationTime;

	@Builder
	public Coupon(Long id, String name, Long minPrice, Long discountPrice, Long productId, ZonedDateTime expirationTime, int maxCouponCount){
		this.id = id;
		this.name = name;
		this.minPrice = minPrice;
		this.discountPrice = discountPrice;
		this.productId = productId;
		this.expirationTime = expirationTime.withZoneSameInstant(ZoneId.systemDefault());
		this.maxCouponCount = maxCouponCount;
	}
}