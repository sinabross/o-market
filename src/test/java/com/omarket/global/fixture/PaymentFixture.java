package com.omarket.global.fixture;

import com.omarket.payment.dto.PaymentType;
import com.omarket.payment.domain.Payment;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static com.omarket.payment.dto.PaymentType.CASH;

public class PaymentFixture {

    public static class Payment1 {
        public static final Long ID = 1L;
        public static final PaymentType TYPE = CASH;
        public static final Long DISCOUNT_PRICE = 2000L;
        public static final Long TOTAL_PRICE = 29000L;
        public static final boolean STATUS = true;
        public static final ZonedDateTime CREATED_AT = ZonedDateTime.of(2022, 1, 1, 20, 30, 0, 0, ZoneId.of("UTC"));

        public static final Payment PAYMENT = Payment.builder()
                .id(ID)
                .type(2)
                .discountPrice(DISCOUNT_PRICE)
                .totalPrice(TOTAL_PRICE)
                .status(STATUS)
                .createdAt(CREATED_AT)
                .build();
    }
}
