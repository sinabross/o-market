package com.omarket.coupon.controller;

import com.omarket.auth.AuthRequired;
import com.omarket.coupon.domain.Coupon;
import com.omarket.coupon.service.CouponService;
import com.omarket.global.common.StatusEnum;
import com.omarket.global.common.SuccessResponse;
import com.omarket.user.service.LoginService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CouponController {

	private final CouponService couponService;
	private final LoginService loginService;

	public CouponController(CouponService couponService, @Qualifier("userSessionLoginService") LoginService loginService){
		this.couponService = couponService;
		this.loginService = loginService;
	}

	@GetMapping("/available-coupons")
	public SuccessResponse getAvailableCoupons(){
		List<Coupon> coupons = couponService.getAvailableCoupons();
		SuccessResponse res = SuccessResponse.builder()
			.status(StatusEnum.OK)
			.message("사용가능한 쿠폰 목록 가져오기 성공")
			.data(coupons)
			.build();
		return res;
	}

	@AuthRequired
	@PostMapping("/available-coupons/{id}")
	public SuccessResponse saveCoupon(@PathVariable("id") final Long id){
		Long userId = loginService.getLoginUserId();
		couponService.saveCoupon(id, userId);
		SuccessResponse res = SuccessResponse.builder()
			.status(StatusEnum.CREATED)
			.message("쿠폰받기 성공")
			.build();
		return res;
	}
}
