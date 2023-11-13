package com.petproject.controllers;

import com.petproject.entity.Book;
import com.petproject.entity.Review;
import com.petproject.service.BookService;
import com.petproject.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {

    private final BookService bookService;
    private final ReviewService reviewService;

    @Autowired
    public AdminController(BookService bookService, ReviewService reviewService) {
        this.bookService = bookService;
        this.reviewService = reviewService;
    }

    /**
     * Returns a page with an admin panel for adding books.
     */
    @GetMapping("/admin-panel")
    public String getAdminPage() {
        return "admin";
    }

    /**
     * Used to add books to the database.
     */
    @PostMapping("/new-book")
    public String addBook(@ModelAttribute Book book) {
        bookService.saveBook(book);
        return "redirect:/admin/admin-panel";
    }

    /**
     * Used to change the book price.
     */
    @PostMapping("/price/{bookId}")
    public String changeBookPrice(@PathVariable Long bookId, @RequestParam double newPrice) {
        bookService.updateBookPrice(bookId, newPrice);
        return "redirect:/books";
    }

    /**
     * Used to change the book quantity.
     */
    @PostMapping("/quantity/{bookId}")
    public String changeBookQuantity(@PathVariable Long bookId, @RequestParam int newQuantity) {
        bookService.updateBookQuantity(bookId, newQuantity);
        return "redirect:/books";
    }

    /**
     * Used to change the book picture.
     */
    @PostMapping("/image/{bookId}")
    public String changeBookImage(@PathVariable Long bookId, @RequestParam String newImage) {
        bookService.updateBookImage(bookId, newImage);
        return "redirect:/books";
    }

    /**
     * Used to set the quantity of books to 0.
     */
    @PostMapping("/book/{bookId}")
    public String deleteBook(@PathVariable Long bookId) {
        bookService.updateBookQuantity(bookId, 0);
        return "redirect:/books";
    }

    /**
     * Used to delete a review.
     */
    @PostMapping("/review")
    public String deleteReview(@RequestParam Long bookId, @RequestParam Long reviewId) {
        Review review = reviewService.getReviewById(reviewId);
        reviewService.deleteReview(review);
        return "redirect:/books/book/{bookId}";
    }
}
