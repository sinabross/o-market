package com.omarket.order.repository;

import com.omarket.order.domain.Order;
import com.omarket.order.domain.OrderProduct;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface OrderRepository {

	void insertOrderInfo(Order order);

	void insertOrderProducts(OrderProduct orderProduct);

	List<Order> findOrdersByUserId(long userId);
}
