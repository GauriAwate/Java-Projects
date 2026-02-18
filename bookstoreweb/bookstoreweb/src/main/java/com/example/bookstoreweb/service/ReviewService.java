package com.example.bookstoreweb.service;

import com.example.bookstoreweb.entity.Book;
import com.example.bookstoreweb.entity.Review;
import com.example.bookstoreweb.entity.User;
import com.example.bookstoreweb.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    // Save a new review with rating and comment
    public void addReview( String comment, Book book, User user) {

        Review review = Review.builder()

                .comment(comment)
                .book(book)
                .user(user)
                .createdAt(LocalDateTime.now())
                .build();
        reviewRepository.save(review);
    }

    // Get all reviews for a specific book
    public List<Review> getReviewsByBookId(Long bookId) {
        return reviewRepository.findByBookId(bookId);
    }
}
