package com.omarket.review.repository;

import com.omarket.review.domain.Review;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ReviewRepository {

    long insertReview(Review review);

}
