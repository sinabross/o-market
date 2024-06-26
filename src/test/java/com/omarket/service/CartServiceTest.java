package com.omarket.service;

import com.omarket.cart.domain.Cart;
import com.omarket.cart.repository.CartRepository;
import com.omarket.cart.service.CartService;
import com.omarket.global.fixture.CartFixture.*;
import com.omarket.global.fixture.ProductFixture.*;
import com.omarket.global.fixture.UserFixture.*;
import com.omarket.product.dto.SaveToCartRequest;
import com.omarket.product.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {
	
	@InjectMocks
	private CartService cartService;

	@Mock
	private ProductService productService;
	
	@Mock
	private CartRepository cartRepository;

	@DisplayName("존재하는 상품을 1개 이상 처음선택시 장바구니 담기에 성공한다.")
	@Test
	void saveProduct() {
		// given
		final SaveToCartRequest dto = SaveToCartRequest.builder()
			.productId(Cart1.PRODUCT_ID)
			.productNum(Cart1.PRODUCT_NUM)
			.build();
		final Optional<Cart> notFoundInCart = Optional.ofNullable(null);

		given(productService.checkIsProductExist(Product1.ID)).willReturn(true);
		given(cartRepository.findByProductId(User1.ID, Cart1.PRODUCT_ID)).willReturn(notFoundInCart);

		// when
		cartService.saveProduct(User1.ID, dto);

		// then
		then(cartRepository).should(times(1)).insertProduct(any());
	}

	@DisplayName("이미 장바구니에 존재하는 상품을 1개 이상 선택시 장바구니 담기에 성공한다.")
	@Test
	void saveProductWithDuplicateItem() {
		// given
		final SaveToCartRequest dto = SaveToCartRequest.builder()
			.productId(Cart1.PRODUCT_ID)
			.productNum(Cart1.PRODUCT_NUM)
			.build();
		final Optional<Cart> cart = Optional.of(Cart1.CART);

		given(productService.checkIsProductExist(Product1.ID)).willReturn(true);
		given(cartRepository.findByProductId(User1.ID, Cart1.PRODUCT_ID)).willReturn(cart);

		// when
		cartService.saveProduct(User1.ID, dto);

		// then
		then(cartRepository).should(times(1)).updateProductNum(Product1.ID, 2);
	}

	@DisplayName("존재하지 않은 상품을 선택시 장바구니 담기에 실패한다.")
	@Test
	void saveProductDoesNotExist() {
		// given
		final SaveToCartRequest dto = SaveToCartRequest.builder()
			.productId(Product2.ID)
			.productNum(1)
			.build();
		given(productService.checkIsProductExist(Product2.ID)).willReturn(false);

		// then
		assertThrows(IllegalArgumentException.class, () -> cartService.saveProduct(User1.ID, dto));
	}

	@DisplayName("0개의 상품을 선택시 장바구니 담기에 실패한다.")
	@Test
	void saveProductZero() {
		// given
		final SaveToCartRequest dto = SaveToCartRequest.builder()
			.productId(Cart1.PRODUCT_ID)
			.productNum(0)
			.build();
		given(productService.checkIsProductExist(Product1.ID)).willReturn(true);

		// then
		assertThrows(IllegalArgumentException.class, () -> cartService.saveProduct(User1.ID, dto));
	}
}
