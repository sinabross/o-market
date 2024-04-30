package com.omarket.coupon.controller;

import com.omarket.global.constant.SessionKey;
import com.omarket.global.fixture.CouponFixture.*;
import com.omarket.global.fixture.UserFixture.*;
import com.omarket.global.template.ControllerTestTemplate;
import com.omarket.user.dto.SignUpRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CouponControllerTest extends ControllerTestTemplate {

	@DisplayName("이벤트/쿠폰을 선택하면 사용 가능한 쿠폰 목록을 보여준다.")
	@Test
	public void getAvailableCoupons() throws Exception{
		//when
		final ResultActions actions = mvc.perform(get("/available-coupons"))
			.andDo(print());

		//then
		actions
			.andExpect(status().isOk())
			.andDo(print());
	}

	@DisplayName("사용자는 사용 가능한 쿠폰 저장에 성공한다.")
	@Test
	public void saveAvailableCoupons() throws Exception{
		// given
		MockHttpSession session = new MockHttpSession();
		session.setAttribute(SessionKey.LOGIN_USER_ID, User1.ID);

		final SignUpRequestDto joinDto = SignUpRequestDto.builder()
			.name(User1.NAME)
			.email(User1.EMAIL)
			.password(User1.PASSWORD)
			.phone(User1.PHONE)
			.build();

		mvc.perform(post("/users/sign-up")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(joinDto)));

		//when
		final ResultActions actions = mvc.perform(post("/available-coupons/{id}", Coupon2.ID)
			.session(session)
			.content(objectMapper.writeValueAsString(Coupon2.COUPON))
			.accept(MediaType.APPLICATION_JSON))
			.andDo(print());

		//then
		actions
			.andExpect(status().isOk())
			.andDo(print());
	}
}