package com.stapply.backend.stapply.service.parser;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Review {
    private String reviewId;
    private String userName;
    private String userImageSrc;
    private Integer score;
    private LocalDateTime dateReview;
    private String review;
    private String answer;
    private LocalDateTime answerDate;
    private Integer likes;
    private String reviewCreatedVersion;
}
