package com.petproject.service;

import com.petproject.entity.Book;
import com.petproject.entity.Person;
import com.petproject.entity.Rating;
import com.petproject.repository.RatingRepo;
import org.springframework.stereotype.Service;

@Service
public class RatingService {

    private final RatingRepo ratingRepo;
    private final BookService bookService;

    public RatingService(RatingRepo ratingRepo, BookService bookService) {
        this.ratingRepo = ratingRepo;
        this.bookService = bookService;
    }

    /**
     * Used to rate a book.
     */
    public void rateBook(Person person, Book book, int mark) {
        Rating rating = ratingRepo.findByPersonAndBook(person, book).orElse(new Rating());
        rating.setPerson(person);
        rating.setBook(book);
        rating.setMark(mark);
        ratingRepo.save(rating);
        book.setRating(book.averageRating());
        bookService.updateBook(book);

    }
}
