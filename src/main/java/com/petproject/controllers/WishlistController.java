package com.petproject.controllers;

import com.petproject.entity.Book;
import com.petproject.entity.Person;
import com.petproject.entity.Wishlist;
import com.petproject.service.BookService;
import com.petproject.service.PurchaseService;
import com.petproject.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/wishlist")
public class WishlistController {
    private final WishlistService wishlistService;
    private final BookService bookService;
    private final PurchaseService purchaseService;

    @Autowired
    public WishlistController(WishlistService wishlistService, BookService bookService, PurchaseService purchaseService) {
        this.wishlistService = wishlistService;
        this.bookService = bookService;
        this.purchaseService = purchaseService;
    }

    /**
     * Returns a wishlist page.
     */
    @GetMapping("/")
    public String wishlist(Model model, @AuthenticationPrincipal Person person) {
        Wishlist wishlist = wishlistService.getWishlistByPerson(person);
        if (wishlist == null) {
            wishlist = new Wishlist();
            wishlist.setPerson(person);
            wishlistService.saveWishlist(wishlist);
        }
        List<Book> books = wishlist.getBooks();
        model.addAttribute("books", books);
        return "wishlist";
    }

    /**
     * Used to add a book to wishlist from a page with a specific book.
     */
    @PostMapping("/adding/{bookId}")
    public String addToWishlist(@AuthenticationPrincipal Person person, @PathVariable Long bookId) {
        Book book = bookService.getBookById(bookId);
        wishlistService.addBookToWishlist(person, book);
        return "redirect:/books/book/{bookId}";
    }

    /**
     * Used to add a book to wishlist from a page with all books.
     */
    @PostMapping("/adding-book/{bookId}")
    public String addBookToWishlist(@AuthenticationPrincipal Person person, @PathVariable Long bookId) {
        Book book = bookService.getBookById(bookId);
        wishlistService.addBookToWishlist(person, book);
        return "redirect:/books";
    }

    /**
     * Used to add a book to wishlist from a page with books by chosen genre.
     */
    @PostMapping("/adding-book-genre/{bookId}")
    public String addBookByGenreToWishlist(@AuthenticationPrincipal Person person, @PathVariable Long bookId,
                                           @RequestParam String selectedGenre, RedirectAttributes redirectAttributes) {
        Book book = bookService.getBookById(bookId);
        wishlistService.addBookToWishlist(person, book);
        redirectAttributes.addAttribute("genre", selectedGenre);
        return "redirect:/books/genre/{genre}";
    }

    /**
     * Used to remove book from wishlist.
     */
    @PostMapping("/removing/{bookId}")
    public String removeFromWishlist(@AuthenticationPrincipal Person person, @PathVariable Long bookId) {
        Book book = bookService.getBookById(bookId);
        wishlistService.removeBookFromWishlist(person, book);
        return "redirect:/wishlist/";
    }

    /**
     * Used to add a book to the cart from wishlist.
     */
    @PostMapping("/cart/adding-book/{bookId}")
    public String addToPurchasesFromWishlist(@AuthenticationPrincipal Person person, @PathVariable Long bookId) {
        Book book = bookService.getBookById(bookId);
        purchaseService.addBookToPurchases(person, book);
        wishlistService.removeBookFromWishlist(person, book);
        return "redirect:/wishlist/";
    }
}
