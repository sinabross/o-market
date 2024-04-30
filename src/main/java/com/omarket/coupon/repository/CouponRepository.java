package com.omarket.coupon.repository;

import com.omarket.coupon.domain.Coupon;
import com.omarket.coupon.domain.UserCoupon;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Mapper
@Repository
public interface CouponRepository {

	List<Coupon> getAllCoupons();

	Optional<Coupon> findCouponById(long id);

	Optional<UserCoupon> findUserCouponById(long couponId, long userId);

	int getMaxCouponCount(long id);

	int getIssuedCount(long couponId, long userId);

	void insertUserCoupon(UserCoupon userCoupon);

	void updateIssuedCount(long couponId, long userId, int issuedCount);

	void updateUseCount(long userId, long couponId, int useCount);
}
