package com.omarket.cart.service;

import com.omarket.cart.domain.Cart;
import com.omarket.cart.repository.CartRepository;
import com.omarket.product.dto.SaveToCartRequest;
import com.omarket.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CartService {

	private final CartRepository cartRepository;
	private final ProductService productService;

	@Transactional
	public void saveProduct(long userId, SaveToCartRequest dto){
		if(!productService.checkIsProductExist(dto.getProductId()))
			throw new IllegalArgumentException("존재하지 않는 상품입니다.");
		if(dto.getProductNum() == 0)
			throw new IllegalArgumentException("0개의 상품은 장바구니에 담을 수 없습니다.");

		Cart cart = Cart.builder()
			.productId(dto.getProductId())
			.productNum(dto.getProductNum())
			.userId(userId)
			.build();
		if(!checkIsProductExistInCart(cart))
			cartRepository.insertProduct(cart);
		else {
			int savedNum = getSavedProductNum(userId, dto.getProductId());
			cartRepository.updateProductNum(dto.getProductId(), savedNum + dto.getProductNum());
		}
	}

	public boolean checkIsProductExistInCart(Cart cart){
		return cartRepository.findByProductId(cart.getUserId(), cart.getProductId()).isPresent();
	}

	public int getSavedProductNum(long userId, long productId){
		Optional<Cart> product = cartRepository.findByProductId(userId, productId);
		return product.get().getProductNum();
	}
}