package com.petproject.service;

import com.petproject.entity.Author;
import com.petproject.entity.Book;
import com.petproject.entity.Genre;
import com.petproject.repository.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class BookService {
    private final BookRepo bookRepo;
    private final AuthorService authorService;


    @Autowired
    public BookService(BookRepo bookRepo, AuthorService authorService) {
        this.bookRepo = bookRepo;
        this.authorService = authorService;
    }

    public List<Book> getAllBooks() {
        return bookRepo.findAll();
    }

    /**
     * Used to search for available books.
     */
    public List<Book> getAllExistingBooks() {
        List<Book> allBooks = getAllBooks();
        List<Book> existingBooks = new ArrayList<>();
        for (Book book : allBooks) {
            if (book.getQuantity() > 0) {
                existingBooks.add(book);
            }
        }
        return existingBooks;
    }

    /**
     * Used to save a new book to the database.
     */
    @Transactional
    public void saveBook(final Book book) {
        for (Author author : book.getAuthors()) {
            List<Author> authors = new ArrayList<>();
            Author existingAuthor = authorService.findByName(author.getFirstName(), author.getLastName());
            if (existingAuthor != null) {
                existingAuthor.getBooks().add(book);
                authors.add(existingAuthor);
            } else {
                author.getBooks().add(book);
                authorService.saveAuthor(author);
                authors.add(author);
            }

            book.setAuthors(authors);
            bookRepo.save(book);
        }
    }

    /**
     * Used to update the data of an existing book.
     */
    public void updateBook(final Book book) {
        bookRepo.save(book);
    }

    public Book getBookById(Long id) {
        return bookRepo.findBookById(id);
    }

    /**
     * Used to change the book price.
     */
    public void updateBookPrice(Long id, Double newPrice) {
        Book book = bookRepo.findBookById(id);
        book.setPrice(newPrice);
        bookRepo.save(book);
    }

    /**
     * Used to change the book quantity.
     */
    public void updateBookQuantity(Long id, int newQuantity) {
        Book book = bookRepo.findBookById(id);
        book.setQuantity(newQuantity);
        bookRepo.save(book);
    }

    /**
     * Used to change the book picture.
     */
    public void updateBookImage(Long id, String newImage) {
        Book book = bookRepo.findBookById(id);
        book.setCoverImage(newImage);
        bookRepo.save(book);
    }

    /**
     * Used to search for available books by genre.
     */
    public List<Book> getBooksByGenre(Genre genre) {
        List<Book> allBooksByGenre = bookRepo.findAllByGenre(genre);
        List<Book> existingBooks = new ArrayList<>();
        for (Book book : allBooksByGenre) {
            if (book.getQuantity() > 0) {
                existingBooks.add(book);
            }
        }
        return existingBooks;
    }

    /**
     * Used to get available books by genre as pages.
     */
    public Page<Book> getPagesBooksByGenre(Genre genre, Pageable pageable) {
        List<Book> existingBooks = getBooksByGenre(genre);

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), existingBooks.size());

        return new PageImpl<>(existingBooks.subList(start, end), pageable, existingBooks.size());
    }

    /**
     * Used to get a random available book.
     */
    public Book getRandomBook() {
        List<Book> books = getAllExistingBooks();
        Random random = new Random();
        int randomIndex = random.nextInt(books.size());
        return books.get(randomIndex);
    }

    /**
     * Used to get sorted available books regarding pagination and filtering.
     */
    public Page<Book> getSortedExistingBooks(String search, String sortBy, int page, int size, HttpSession session) {

        // Determining a search value from a parameter or session.
        String finalSearch = Optional.ofNullable(search).orElse((String) session.getAttribute("search"));

        // Determining a sort value from a parameter or session.
        String finalSortBy = Optional.ofNullable(sortBy).orElse((String) session.getAttribute("sortBy"));

        // Creating a Book Search Specification.
        Specification<Book> spec = createBookSpecification(finalSearch);

        // Creating a Pageable object for pagination and sorting.
        Pageable pageable = getPageable(finalSortBy, page, size);

        // Obtaining sorted books taking into account pagination and filtering.
        return bookRepo.findAll(spec, pageable);
    }

    /**
     * Creating Specifications to Define Books.
     */
    private Specification<Book> createBookSpecification(final String finalSearch) {
        return (root, query, cb) -> {
            query.distinct(true);
            List<Predicate> predicates = new ArrayList<>();

            if (finalSearch != null) {
                Join<Book, Author> authorJoin = root.join("authors");
                String searchPattern = "%" + finalSearch.toLowerCase() + "%";

                // Adding predicates for searching by first name and last name of the author.
                predicates.add(cb.or(
                        cb.like(cb.lower(root.get("name")), searchPattern),
                        cb.like(cb.lower(authorJoin.get("firstName")), searchPattern),
                        cb.like(cb.lower(authorJoin.get("lastName")), searchPattern)
                ));

                // Adding predicates for searching by genre.
                for (Genre genre : Genre.values()) {
                    if (genre.getDisplayName().toLowerCase().contains(finalSearch.toLowerCase())) {
                        predicates.add(cb.equal(root.get("genre"), genre));
                    }
                }
            }

            // Adding a predicate to filter books with a positive count.
            predicates.add(cb.greaterThan(root.get("quantity"), 0));
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    /**
     * Creating a Pageable object for pagination and sorting.
     */
    private Pageable getPageable(String sortBy, int page, int size) {
        Sort sort = null;

        if (sortBy != null) {
            switch (sortBy) {
                case "name" -> sort = Sort.by("name");
                case "priceAsc" -> sort = Sort.by("price");
                case "priceDesc" -> sort = Sort.by("price").descending();
                case "rating" -> sort = Sort.by("rating").descending();
            }
        }

        // Obtaining a Pageable object based on sorting.
        return sort != null ? PageRequest.of(page, size, sort) : PageRequest.of(page, size);
    }

}
