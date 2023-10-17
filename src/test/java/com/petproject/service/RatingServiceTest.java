package com.petproject.service;

import com.petproject.entity.Book;
import com.petproject.entity.Person;
import com.petproject.entity.Rating;
import com.petproject.repository.RatingRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RatingServiceTest {
    private RatingRepo ratingRepo;
    private BookService bookService;
    private RatingService ratingService;

    @BeforeEach
    public void setUp() {
        ratingRepo = Mockito.mock(RatingRepo.class);
        bookService = Mockito.mock(BookService.class);
        ratingService = new RatingService(ratingRepo, bookService);
    }

    @Test
    public void rateBookWhenRatingDoesNotExist() {
        Person person = new Person();
        Book book = new Book();
        int mark = 5;

        when(ratingRepo.findByPersonAndBook(any(Person.class), any(Book.class))).thenReturn(Optional.empty());

        ratingService.rateBook(person, book, mark);

        ArgumentCaptor<Rating> ratingCaptor = ArgumentCaptor.forClass(Rating.class);
        ArgumentCaptor<Book> bookCaptor = ArgumentCaptor.forClass(Book.class);
        verify(ratingRepo, times(1)).findByPersonAndBook(person, book);
        verify(ratingRepo, times(1)).save(ratingCaptor.capture());
        verify(bookService, times(1)).updateBook(bookCaptor.capture());

        Rating savedRating = ratingCaptor.getValue();
        Book updatedBook = bookCaptor.getValue();
        assertEquals(person, savedRating.getPerson());
        assertEquals(book, savedRating.getBook());
        assertEquals(mark, savedRating.getMark());
        assertEquals(book.averageRating(), updatedBook.getRating());
    }

    @Test
    public void rateBookWhenRatingExists() {
        Person person = new Person();
        Book book = new Book();
        int mark = 5;
        Rating rating = new Rating();

        when(ratingRepo.findByPersonAndBook(any(Person.class), any(Book.class))).thenReturn(Optional.of(rating));

        ratingService.rateBook(person, book, mark);

        ArgumentCaptor<Rating> ratingCaptor = ArgumentCaptor.forClass(Rating.class);
        ArgumentCaptor<Book> bookCaptor = ArgumentCaptor.forClass(Book.class);
        verify(ratingRepo, times(1)).findByPersonAndBook(person, book);
        verify(ratingRepo, times(1)).save(ratingCaptor.capture());
        verify(bookService, times(1)).updateBook(bookCaptor.capture());

        Rating savedRating = ratingCaptor.getValue();
        Book updatedBook = bookCaptor.getValue();
        assertEquals(person, savedRating.getPerson());
        assertEquals(book, savedRating.getBook());
        assertEquals(mark, savedRating.getMark());
        assertEquals(book.averageRating(), updatedBook.getRating());
    }
}
