package com.omarket.review.service;


import com.omarket.global.util.strorage.Storage;
import com.omarket.review.dto.CreateReviewRequest;
import com.omarket.review.dto.EvaluateReviewRequest;
import com.omarket.review.domain.Review;
import com.omarket.review.domain.ReviewEvaluation;
import com.omarket.review.repository.ReviewEvaluationRepository;
import com.omarket.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;


@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewEvaluationRepository reviewEvaluationRepository;
    @Qualifier("ncpStorage")
    private final Storage storage;

    @Transactional
    public void createReview(long userId, CreateReviewRequest dto){
        Review review = dto.toEntity(userId);
        reviewRepository.insertReview(review);

        long reviewId = review.getId();
        MultipartFile img = dto.getImg();
        storage.saveMultipartFile(img, String.valueOf(reviewId));
    }

    @Transactional
    public void evaluateReview(long userId, EvaluateReviewRequest dto){
        ReviewEvaluation reviewEvaluation = dto.toEntity(userId);
        if (checkIsExistReviewEvaluation(userId, dto.getReviewId())) {
            reviewEvaluationRepository.updateReviewEvaluation(userId, reviewEvaluation.getReviewId(), reviewEvaluation.isHelp());
        } else {
            reviewEvaluationRepository.insertReviewEvaluation(reviewEvaluation);
        }
    }

    private boolean checkIsExistReviewEvaluation(long userId, long reveiwId){
        Optional<ReviewEvaluation> pastReviewEvaluation = reviewEvaluationRepository.getReviewEvaluationByUserIdAndReviewId(userId, reveiwId);
        return pastReviewEvaluation.isPresent();
    }

}
