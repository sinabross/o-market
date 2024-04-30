package com.omarket.payment.service;

import com.omarket.payment.dto.PaymentDto;
import com.omarket.payment.domain.Payment;
import com.omarket.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PaymentService {

	private final PaymentRepository paymentRepository;

	public void pay(PaymentDto paymentDto){
		if(!mockPayment(paymentDto))
			throw new IllegalArgumentException("결제에 실패하였습니다.");
	}

	public long savePaymentInfo(Payment payment){
		paymentRepository.insertPaymentInfo(payment);
		return payment.getId();
	}

	public boolean mockPayment(PaymentDto dto){
		return true;
	}
}
