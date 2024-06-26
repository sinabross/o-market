package com.omarket.product.controller;


import com.omarket.global.constant.SessionKey;
import com.omarket.global.fixture.CartFixture.*;
import com.omarket.global.fixture.UserFixture;
import com.omarket.global.template.ControllerTestTemplate;
import com.omarket.global.util.MultiValueMapConverter;
import com.omarket.product.dto.GetProductsRequest;
import com.omarket.product.dto.SaveToCartRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.MultiValueMap;

import static com.omarket.product.constant.DeliveryTypeEnum.ROCKET;
import static com.omarket.product.constant.DeliveryTypeEnum.ROCKET_FRESH;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class ProductControllerTest extends ControllerTestTemplate {

    @DisplayName("로켓 배송을 선택하면 로켓 배송 가능 상품 목록을 보여준다.")
    @Test
    public void getProductsByIsRocket() throws Exception {
        // given
        final GetProductsRequest dto = GetProductsRequest.builder()
                .deliveryType(ROCKET)
                .start(1)
                .listSize(2)
                .build();
        final MultiValueMap<String, String> params = MultiValueMapConverter.convert(objectMapper, dto);

        // when
        final ResultActions actions = mvc.perform(get("/products")
            .params(params))
            .andDo(print());

        // then
        actions
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("로켓 프레쉬 배송을 선택하고 로켓 배송 필터링을 적용하면 로켓 프레쉬와 로켓 배송 모두 가능한 상품 전체를 가져온다.")
    @Test
    public void getProductsByIsRocketAndIsRocketFresh() throws Exception {
        // given
        final GetProductsRequest dto = GetProductsRequest.builder()
                .deliveryType(ROCKET_FRESH)
                .rocket(true)
                .start(1)
                .listSize(2)
                .build();
        final MultiValueMap<String, String> params = MultiValueMapConverter.convert(objectMapper, dto);

        // when
        final ResultActions actions = mvc.perform(get("/products")
                .params(params))
                .andDo(print());

        // then
        actions
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("키워드로 검색하면 키워드를 포함한 상품 목록을 가져온다.")
    @Test
    public void searchProductsByKeyword() throws Exception {
        // given
        final String keyword = "123";

        // when
        final ResultActions actions = mvc.perform(get("/products/search")
                .param("keyword", keyword))
                .andDo(print());

        // then
        actions
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("상품과 수량을 선택하면 장바구니에 담긴다.")
    @Test
    public void saveProductsToCart() throws Exception {
        // given
        MockHttpSession session = new MockHttpSession();
        session.setAttribute(SessionKey.LOGIN_USER_ID, UserFixture.User1.ID);

        final SaveToCartRequest dto = SaveToCartRequest.builder()
            .productId(Cart1.PRODUCT_ID)
            .productNum(Cart1.PRODUCT_NUM)
            .build();

        // when
        final ResultActions actions = mvc.perform(post("/products/cart")
            .session(session)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)))
            .andDo(print());

        // then
        actions
            .andExpect(status().isOk())
            .andDo(print());
    }
}
