package com.omarket.global.fixture;

import com.omarket.coupon.domain.Coupon;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class CouponFixture {

	public static class Coupon1 {
		public static final Long ID = 1L;
		public static final String NAME = "coupon1";
		public static final Long MIN_PRICE = 30000L;
		public static final Long DISCOUNT_PRICE = 2000L;
		public static final Long PRODUCT_ID = 1L;
		public static final ZonedDateTime EXPIRATION_TIME = ZonedDateTime.of(2022, 12, 01, 20, 30, 0, 0, ZoneId.of("UTC"));
		public static final Integer MAX_COUPON_COUNT = 5;

		public static final Coupon COUPON = Coupon.builder()
			.id(ID)
			.name(NAME)
			.minPrice(MIN_PRICE)
			.discountPrice(DISCOUNT_PRICE)
			.productId(PRODUCT_ID)
			.expirationTime(EXPIRATION_TIME)
			.maxCouponCount(MAX_COUPON_COUNT)
			.build();
	}

	public static class Coupon2 {
		public static final Long ID = 2L;
		public static final String NAME = "coupon2";
		public static final Long MIN_PRICE = 20000L;
		public static final Long DISCOUNT_PRICE = 1000L;
		public static final Long PRODUCT_ID = 11L;
		public static final ZonedDateTime EXPIRATION_TIME = ZonedDateTime.of(2023, 12, 05, 14, 00, 0, 0, ZoneId.of("UTC"));
		public static final Integer MAX_COUPON_COUNT = 5;

		public static final Coupon COUPON = Coupon.builder()
			.id(ID)
			.name(NAME)
			.minPrice(MIN_PRICE)
			.discountPrice(DISCOUNT_PRICE)
			.productId(PRODUCT_ID)
			.expirationTime(EXPIRATION_TIME)
			.maxCouponCount(MAX_COUPON_COUNT)
			.build();
	}

	public static class Coupon3 {
		public static final Long ID = 3L;
		public static final String NAME = "coupon3";
		public static final Long MIN_PRICE = 40000L;
		public static final Long DISCOUNT_PRICE = 5000L;
		public static final Long PRODUCT_ID = 11L;
		public static final ZonedDateTime EXPIRATION_TIME = ZonedDateTime.of(2020, 12, 05, 14, 00, 0, 0, ZoneId.of("UTC"));
		public static final Integer MAX_COUPON_COUNT = 5;

		public static final Coupon COUPON = Coupon.builder()
			.id(ID)
			.name(NAME)
			.minPrice(MIN_PRICE)
			.discountPrice(DISCOUNT_PRICE)
			.productId(PRODUCT_ID)
			.expirationTime(EXPIRATION_TIME)
			.maxCouponCount(MAX_COUPON_COUNT)
			.build();
	}
}