package com.omarket.coupon.service;

import com.omarket.coupon.domain.Coupon;
import com.omarket.coupon.domain.UserCoupon;
import com.omarket.coupon.repository.CouponRepository;
import com.omarket.global.fixture.CouponFixture.*;
import com.omarket.global.fixture.UserCouponFixture.*;
import com.omarket.global.fixture.UserFixture.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class CouponServiceTest {

	@InjectMocks
	private CouponService couponService;

	@Mock
	private CouponRepository couponRepository;

	@DisplayName("만료시간이 지나지 않은 쿠폰을 모두 가져온다.")
	@Test
	public void getCouponsBeforeExpirationTime() {
		//given
		given(couponRepository.getAllCoupons())
			.willReturn(Arrays.asList(Coupon1.COUPON, Coupon2.COUPON, Coupon3.COUPON));

		//then
		assertThat(couponService.getAvailableCoupons().size()).isEqualTo(2);
	}

	@DisplayName("만료시간이 지나지 않은 쿠폰이면 저장에 성공한다.")
	@Test
	public void saveCouponBeforeExpirationTime() {
		//given
		final Optional<Coupon> FoundCoupon = Optional.ofNullable(Coupon1.COUPON);
		given(couponRepository.findCouponById(Coupon1.ID)).willReturn(FoundCoupon);
		given(couponRepository.getMaxCouponCount(Coupon1.ID)).willReturn(Coupon1.MAX_COUPON_COUNT);

		final Optional<UserCoupon> notFoundUserCoupon = Optional.ofNullable(null);
		given(couponRepository.findUserCouponById(Coupon1.ID, User1.ID)).willReturn(notFoundUserCoupon);

		//when
		couponService.saveCoupon(Coupon1.ID, User1.ID);

		//then
		then(couponRepository).should(times(1)).insertUserCoupon(any());
	}

	@DisplayName("만료시간이 지난 쿠폰은 저장에 실패한다.")
	@Test
	public void saveCouponAfterExpirationTime() {
		//given
		final Optional<Coupon> FoundCoupon = Optional.ofNullable(Coupon3.COUPON);
		given(couponRepository.findCouponById(Coupon3.ID)).willReturn(FoundCoupon);

		//then
		assertThrows(IllegalArgumentException.class, () -> couponService.saveCoupon(Coupon3.ID, User1.ID));
	}

	@DisplayName("이미 존재하는 쿠폰의 발급이 가능한 상태이면 쿠폰 저장에 성공한다.")
	@Test
	public void saveCouponAlreadyIssuedCoupon() {
		//given
		final Optional<UserCoupon> FoundUserCoupon = Optional.ofNullable(UserCoupon1.USERCOUPON);
		given(couponRepository.findUserCouponById(Coupon2.ID, User1.ID)).willReturn(FoundUserCoupon);

		final Optional<Coupon> FoundCoupon = Optional.ofNullable(Coupon2.COUPON);
		given(couponRepository.findCouponById(Coupon2.ID)).willReturn(FoundCoupon);
		given(couponRepository.getMaxCouponCount(Coupon2.ID)).willReturn(Coupon2.MAX_COUPON_COUNT);
		given(couponRepository.getIssuedCount(Coupon2.ID, User1.ID)).willReturn(UserCoupon1.ISSUED_COUNT);

		//when
		couponService.saveCoupon(Coupon2.ID, User1.ID);

		//then
		then(couponRepository).should(times(1)).updateIssuedCount(Coupon2.ID, User1.ID, UserCoupon1.ISSUED_COUNT+1);
	}

	@DisplayName("발급받을 수 있는 최대 쿠폰의 수만큼 발행하였을 경우 쿠폰 저장에 실패한다.")
	@Test
	public void saveCouponAlreadyIssuedMaxCouponCount() {
		//given
		final Optional<UserCoupon> FoundUserCoupon = Optional.ofNullable(UserCoupon2.USERCOUPON);
		given(couponRepository.findUserCouponById(Coupon2.ID, User2.ID)).willReturn(FoundUserCoupon);

		final Optional<Coupon> FoundCoupon = Optional.ofNullable(Coupon2.COUPON);
		given(couponRepository.findCouponById(Coupon2.ID)).willReturn(FoundCoupon);
		given(couponRepository.getMaxCouponCount(Coupon2.ID)).willReturn(Coupon2.MAX_COUPON_COUNT);
		given(couponRepository.getIssuedCount(Coupon2.ID, User2.ID)).willReturn(UserCoupon2.ISSUED_COUNT);

		//then
		assertThrows(IllegalArgumentException.class, () -> couponService.saveCoupon(Coupon2.ID, User2.ID));
	}
}