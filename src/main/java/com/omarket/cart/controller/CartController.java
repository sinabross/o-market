package com.omarket.cart.controller;

import com.omarket.auth.AuthRequired;
import com.omarket.cart.service.CartService;
import com.omarket.global.common.StatusEnum;
import com.omarket.global.common.SuccessResponse;
import com.omarket.product.dto.SaveToCartRequest;
import com.omarket.user.service.LoginService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class CartController {

	private final CartService cartService;
	private final LoginService loginService;

	public CartController(CartService cartService, @Qualifier("userSessionLoginService") LoginService loginService){
		this.cartService = cartService;
		this.loginService = loginService;
	}

	@AuthRequired
	@PostMapping("/carts")
	public SuccessResponse saveProductsToCart(@Valid @RequestBody final SaveToCartRequest dto){
		long userId = loginService.getLoginUserId();
		cartService.saveProduct(userId, dto);
		return SuccessResponse.builder()
			.status(StatusEnum.OK)
			.message("장바구니에 상품 담기 성공")
			.build();
	}
}
