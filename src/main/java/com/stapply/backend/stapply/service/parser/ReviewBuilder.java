package com.stapply.backend.stapply.service.parser;

import java.time.LocalDateTime;

public class ReviewBuilder {
    private Review review;

    public ReviewBuilder() {
        this.review = new Review();
    }

    public ReviewBuilder setReviewId(String reviewId) {
        this.review.setReviewId(reviewId);
        return this;
    }

    public ReviewBuilder setUserName(String userName) {
        this.review.setUserName(userName);
        return this;
    }

    public ReviewBuilder setUserImageSrc(String userImageSrc) {
        this.review.setUserImageSrc();
    }
}
