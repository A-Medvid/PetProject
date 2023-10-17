package com.petproject.service;

import com.petproject.entity.Book;
import com.petproject.entity.Person;
import com.petproject.entity.Review;
import com.petproject.repository.ReviewRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class ReviewServiceTest {
    @Mock
    ReviewRepo reviewRepo;

    @Mock
    PersonService personService;

    @InjectMocks
    ReviewService reviewService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldSaveReview() {
        Person person = new Person();
        person.setId(1L);
        Book book = new Book();
        String reviewText = "TEST Review!";

        Person mockPerson = new Person();
        mockPerson.setId(1L);

        when(personService.getPersonById(person.getId())).thenReturn(mockPerson);

        reviewService.saveReview(person, book, reviewText);

        ArgumentCaptor<Review> commentCaptor = ArgumentCaptor.forClass(Review.class);
        verify(reviewRepo, times(1)).save(commentCaptor.capture());

        Review providedReview = commentCaptor.getValue();
        Assertions.assertEquals(mockPerson, providedReview.getPerson());
        Assertions.assertEquals(book, providedReview.getBook());
        Assertions.assertEquals(reviewText, providedReview.getText());
    }

    @Test
    void shouldGetReviewById() {
        Long reviewId = 1L;
        Review expectedReview = new Review();

        when(reviewRepo.getReviewById(reviewId)).thenReturn(expectedReview);

        Review resultReview = reviewService.getReviewById(reviewId);

        verify(reviewRepo, times(1)).getReviewById(reviewId);
        Assertions.assertEquals(expectedReview, resultReview);
    }

    @Test
    void shouldDeleteReview() {
        Review review = new Review();

        reviewService.deleteReview(review);

        verify(reviewRepo, times(1)).delete(review);
    }
}
