package com.omarket.review.service;

import com.omarket.global.fixture.ImageFixture.*;
import com.omarket.global.fixture.ReviewEvaluationFixture.*;
import com.omarket.global.fixture.ReviewFixture.*;
import com.omarket.global.fixture.UserFixture.*;
import com.omarket.global.util.strorage.Storage;
import com.omarket.review.dto.CreateReviewRequest;
import com.omarket.review.dto.EvaluateReviewRequest;
import com.omarket.review.domain.Evaluation;
import com.omarket.review.domain.ReviewEvaluation;
import com.omarket.review.repository.ReviewEvaluationRepository;
import com.omarket.review.repository.ReviewRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {

    @InjectMocks
    private ReviewService reviewService;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private ReviewEvaluationRepository reviewEvaluationRepository;

    @Mock
    private Storage storage;

    @DisplayName("리뷰 등록에 성공한다.")
    @Test
    void createReview() {
        // given
        final long userId = User1.ID;
        final CreateReviewRequest dto = CreateReviewRequest.builder()
                .productId(Review1.PRODUCT_ID)
                .score(Review1.SCORE)
                .content(Review1.CONTENT)
                .img(Image1.MULTIPART_FILE)
                .build();

        // when
        reviewService.createReview(userId, dto);

        // then
        then(reviewRepository).should(times(1)).insertReview(any());
        then(storage).should(times(1)).saveMultipartFile(any(), any());

    }

    @DisplayName("이미 리뷰 평가가 있으면 삭제 후, 리뷰 평가를 추가한다.")
    @Test
    void evaluateReviewAgain() {
        // given
        final long userId = ReviewEvaluation1.USER_ID;
        final EvaluateReviewRequest dto = EvaluateReviewRequest.builder()
                .reviewId(ReviewEvaluation1.REVIEW_ID)
                .evaluation(Evaluation.HELP)
                .build();
        final Optional<ReviewEvaluation> reviewEvaluation = Optional.of(ReviewEvaluation1.REVIEW_EVALUATION);
        // given(reviewEvaluationRepository.getReviewEvaluationByUserIdAndReviewId(any(), any())).willReturn(reviewEvaluation);

        // when
        reviewService.evaluateReview(userId, dto);

        // then
        // then(reviewEvaluationRepository).should(times(1)).updateReviewEvaluation(any(), any(), any());
    }

    @DisplayName("리뷰 평가가 처음이면, 리뷰 평가를 추가한다.")
    @Test
    void evaluateReviewFirstTime() {
        // given
        final long userId = ReviewEvaluation1.USER_ID;
        final EvaluateReviewRequest dto = EvaluateReviewRequest.builder()
                .reviewId(ReviewEvaluation1.REVIEW_ID)
                .evaluation(Evaluation.HELP)
                .build();
        final Optional<ReviewEvaluation> notExistReviewEvaluation = Optional.ofNullable(null);
        // given(reviewEvaluationRepository.getReviewEvaluationByUserIdAndReviewId(any(), any())).willReturn(notExistReviewEvaluation);

        // when
        reviewService.evaluateReview(userId, dto);

        // then
        then(reviewEvaluationRepository).should(times(1)).insertReviewEvaluation(any());
    }
}