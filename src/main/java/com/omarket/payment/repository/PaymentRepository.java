package com.omarket.payment.repository;

import com.omarket.payment.domain.Payment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface PaymentRepository {

	void insertPaymentInfo(Payment payment);
}
