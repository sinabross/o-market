package com.omarket.cart.repository;

import com.omarket.cart.domain.Cart;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Mapper
@Repository
public interface CartRepository {

	List<Cart> findByUserId(long userId);

	void insertProduct(Cart cart);

	void updateProductNum(long productId, int productNum);

	Optional<Cart> findByProductId(long userId, long productId);

	void deleteCartProducts(Cart cart);
}