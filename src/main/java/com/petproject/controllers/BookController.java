package com.petproject.controllers;

import com.petproject.entity.Book;
import com.petproject.entity.Person;
import com.petproject.service.BookService;
import com.petproject.service.RatingService;
import com.petproject.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class BookController {
    private final BookService bookService;
    private final ReviewService reviewService;
    private final RatingService ratingService;

    @Autowired
    public BookController(BookService bookService, ReviewService reviewService, RatingService ratingService) {
        this.bookService = bookService;
        this.reviewService = reviewService;
        this.ratingService = ratingService;
    }

    /**
     * Retrieves information about a specific book and related books of the same genre
     * and displays them on the "book" page.
     */
    @GetMapping("/books/book/{id}")
    public String getBook(@PathVariable Long id, Model model) {
        Book book = bookService.getBookById(id);
        List<Book> booksList = bookService.getBooksByGenre(book.getGenre());

        // Get a list of books of the same genre, excluding the current book.
        booksList.remove(book);
        List<Book> books = booksList.stream().limit(3).collect(Collectors.toList());

        model.addAttribute("book", book);
        model.addAttribute("books", books);
        return "book";
    }

    /**
     * Used to add a review.
     */
    @PostMapping("/new-review/{bookId}")
    public String changeBookQuantity(@PathVariable Long bookId,
                                     @AuthenticationPrincipal Person user,
                                     @RequestParam String review) {
        Book book = bookService.getBookById(bookId);
        reviewService.saveReview(user, book, review);
        return "redirect:/books/book/" + bookId;
    }

    /**
     * Used to rate a book.
     */
    @PostMapping("/rate-book/{bookId}")
    public String rateBook(@PathVariable Long bookId,
                           @AuthenticationPrincipal Person user,
                           @RequestParam int mark) {
        Book book = bookService.getBookById(bookId);
        ratingService.rateBook(user, book, mark);
        return "redirect:/books/book/" + bookId;
    }
}
