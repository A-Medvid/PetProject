package com.petproject.controllers;

import com.petproject.entity.Book;
import com.petproject.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpSession;

@Controller
@SessionAttributes("books")
public class BooksController {
    private final BookService bookService;

    @Autowired
    public BooksController(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * Change this number to change the number of books per page.
     */
    private static final int DEFAULT_PAGE_SIZE = 3;

    /**
     * Handles requests to display a page containing all books.
     */
    @GetMapping("/books")
    public String getAllBooks(Model model,
                              @RequestParam(required = false) String search,
                              @RequestParam(defaultValue = "0") int page,
                              @RequestParam(required = false, defaultValue = "name") String sortOption,
                              HttpSession session) {
        session.setAttribute("sortOption", sortOption);
        addBookAttributesToModel(model, search, sortOption, page, session);
        return "books";
    }

    /**
     * Handles requests to display a page containing all books sorted by a specified option.
     */
    @GetMapping("/books/sort")
    public String sortBooks(Model model,
                            @RequestParam(required = false) String sortOption,
                            @RequestParam(defaultValue = "0") int page,
                            HttpSession session) {
        session.setAttribute("sortOption", sortOption);
        String search = (String) session.getAttribute("search");
        addBookAttributesToModel(model, search, sortOption, page, session);
        return "books";
    }

    /**
     * Adds attributes related to books to the model for rendering.
     */
    private void addBookAttributesToModel(Model model, String search, String sortOption, int page, HttpSession session) {
        Page<Book> books = bookService.getSortedExistingBooks(search, sortOption, page, DEFAULT_PAGE_SIZE, session);
        model.addAttribute("books", books);
        model.addAttribute("search", search);
        model.addAttribute("sortOption", sortOption);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", books.getTotalPages());
    }
}
