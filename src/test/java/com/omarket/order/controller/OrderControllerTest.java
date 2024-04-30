package com.omarket.order.controller;

import com.omarket.global.constant.SessionKey;
import com.omarket.global.fixture.PaymentFixture.*;
import com.omarket.global.fixture.UserFixture.*;
import com.omarket.global.template.ControllerTestTemplate;
import com.omarket.order.dto.OrderRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class OrderControllerTest extends ControllerTestTemplate {

    @DisplayName("사용자는 장바구니에 있는 상품 주문에 성공한다.")
    @Test
    public void order() throws Exception{
        // given
        MockHttpSession session = new MockHttpSession();
        session.setAttribute(SessionKey.LOGIN_USER_ID, User1.ID);

        final OrderRequestDto dto = OrderRequestDto.builder()
                .receiverName(User1.NAME)
                .receiverPhone(User1.PHONE)
                .type(Payment1.TYPE)
                .receiverRequest("문 앞")
                .build();

        //when
        final ResultActions actions = mvc.perform(post("/orders")
                        .session(session)
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andDo(print());

        //then
        actions
                .andExpect(status().isOk())
                .andDo(print());
    }
}
