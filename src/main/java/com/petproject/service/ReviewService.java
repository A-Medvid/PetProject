package com.petproject.service;

import com.petproject.entity.Book;
import com.petproject.entity.Person;
import com.petproject.entity.Review;
import com.petproject.repository.ReviewRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    private final PersonService personService;
    private final ReviewRepo reviewRepo;

    @Autowired
    public ReviewService(PersonService personService, ReviewRepo reviewRepo) {
        this.personService = personService;
        this.reviewRepo = reviewRepo;
    }

    public void saveReview(Person person, Book book, String commentText) {
        Person personById = personService.getPersonById(person.getId());

        Review review = new Review();
        review.setPerson(personById);
        review.setBook(book);
        review.setText(commentText);

        reviewRepo.save(review);
    }

    public Review getReviewById(Long id) {
        return reviewRepo.getReviewById(id);
    }

    public void deleteReview(Review comment) {
        reviewRepo.delete(comment);
    }

}

