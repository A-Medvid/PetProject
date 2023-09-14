package com.petproject.controllers;

import com.petproject.entity.Book;
import com.petproject.entity.Genre;
import com.petproject.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GenreController {
    private final BookService bookService;

    @Autowired
    public GenreController(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * Change this number to change the number of books per page.
     */
    private static final int DEFAULT_PAGE_SIZE = 3;

    /**
     * Returns a page with books by chosen genre.
     */
    @GetMapping("/books/genre/{genre}")
    public String getBooksByGenre(@PathVariable String genre, Model model,
                                  @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, DEFAULT_PAGE_SIZE);
        Page<Book> booksPage = bookService.getPagesBooksByGenre(Genre.valueOf(genre), pageable);
        model.addAttribute("booksPage", booksPage);
        model.addAttribute("selectedGenre", Genre.getDisplayNameForGenre(genre));
        return "genre";
    }
}
