package com.petproject.controllers;

import com.petproject.entity.Book;
import com.petproject.entity.Person;
import com.petproject.entity.Purchase;
import com.petproject.service.BookService;
import com.petproject.service.PurchaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class PurchaseController {
    private final PurchaseService purchaseService;
    private final BookService bookService;
    private static final Logger LOGGER = LoggerFactory.getLogger(PurchaseController.class);

    @Autowired
    public PurchaseController(PurchaseService purchaseService, BookService bookService) {
        this.purchaseService = purchaseService;
        this.bookService = bookService;
    }

    /**
     * Returns a cart page.
     */
    @GetMapping("/")
    public String cart(Model model, @AuthenticationPrincipal Person person) {
        Purchase purchase = purchaseService.getOpenPurchaseByPerson(person);
        if (purchase == null) {
            purchase = new Purchase();
            purchase.setPerson(person);
            purchaseService.savePurchase(purchase);
        }
        List<Book> books = purchase.getBooks();
        double totalPrice = books.stream().mapToDouble(Book::getPrice).sum();
        model.addAttribute("books", books);
        model.addAttribute("totalPrice", totalPrice);
        return "cart";
    }

    /**
     * Used to add a book to the cart from a page with a specific book.
     */
    @PostMapping("/adding/{bookId}")
    public String addToPurchases(@AuthenticationPrincipal Person person, @PathVariable Long bookId) {
        Book book = bookService.getBookById(bookId);
        purchaseService.addBookToPurchases(person, book);
        return "redirect:/books/book/" + bookId;
    }

    /**
     * Used to add a book to the cart from a page with all books.
     */
    @PostMapping("/adding-book/{bookId}")
    public String addBookToPurchases(@AuthenticationPrincipal Person person, @PathVariable Long bookId) {
        Book book = bookService.getBookById(bookId);
        purchaseService.addBookToPurchases(person, book);
        return "redirect:/books";
    }

    /**
     * Used to add a book to the cart from a page with books by chosen genre.
     */
    @PostMapping("/adding-book-genre/{bookId}")
    public String addBookByGenreToPurchases(@AuthenticationPrincipal Person person,
                                            @PathVariable Long bookId,
                                            @RequestParam String selectedGenre,
                                            RedirectAttributes redirectAttributes) {
        Book book = bookService.getBookById(bookId);
        purchaseService.addBookToPurchases(person, book);
        redirectAttributes.addAttribute("genre", selectedGenre);
        return "redirect:/books/genre/{genre}";
    }

    /**
     * Used to remove book from cart.
     */
    @PostMapping("/removing/{bookId}")
    public String removeFromPurchases(@AuthenticationPrincipal Person person, @PathVariable Long bookId) {
        Book book = bookService.getBookById(bookId);
        purchaseService.removeBookFromPurchases(person, book);
        return "redirect:/cart/";
    }

    /**
     * Used to make a purchase.
     */
    @PostMapping("/checkout")
    public String checkout(@AuthenticationPrincipal Person person) {
        try {
            Purchase purchase = purchaseService.getOpenPurchaseByPerson(person);
            purchase.setDate(LocalDate.now());
            purchaseService.checkout(person);
            purchaseService.savePurchase(purchase);
            LOGGER.info("Checkout completed successful for {}", person.getUsername());
            return "thankYouPage";
        } catch (Exception e) {
            LOGGER.error("An error occurred during checkout for user {}: {}", person.getUsername(), e.getMessage());
            return "redirect:/cart/";
        }
    }

    /**
     * Returns a page with closed purchases.
     */
    @GetMapping("/purchases")
    public String purchases(Model model, @AuthenticationPrincipal Person person) {
        List<Purchase> purchases = purchaseService.getClosedPurchaseByPerson(person);
        model.addAttribute("purchases", purchases);
        return "purchases";
    }
}