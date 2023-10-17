package com.petproject.service;

import com.petproject.entity.Author;
import com.petproject.entity.Book;
import com.petproject.repository.BookRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepo bookRepo;

    @Mock
    private AuthorService authorService;

    @InjectMocks
    private BookService bookService;

    @Test
    void shouldGetAllBooks() {
        Book book1 = new Book();
        Book book2 = new Book();

        when(bookRepo.findAll()).thenReturn(Arrays.asList(book1, book2));

        List<Book> books = bookService.getAllBooks();

        Assertions.assertEquals(2, books.size());
        verify(bookRepo, times(1)).findAll();
    }

    @Test
    void shouldSaveBook() {
        Book book = new Book();
        Author author = new Author();
        book.setAuthors(List.of(author));

        when(authorService.findByName(author.getFirstName(), author.getLastName())).thenReturn(null);
        when(bookRepo.save(book)).thenReturn(book);

        bookService.saveBook(book);

        verify(authorService, times(1)).saveAuthor(author);
        verify(bookRepo, times(1)).save(book);
    }

    @Test
    void shouldUpdateBook() {
        Book book = new Book();

        bookService.updateBook(book);

        verify(bookRepo, times(1)).save(book);
    }

    @Test
    void shouldGetBookById() {
        Long id = 1L;
        Book book = new Book();

        when(bookRepo.findBookById(id)).thenReturn(book);

        Book resultBook = bookService.getBookById(id);

        Assertions.assertEquals(book, resultBook);
        verify(bookRepo, times(1)).findBookById(id);
    }

    @Test
    void shouldGetAllExistingBooks() {
        Book book1 = new Book();
        book1.setQuantity(0);
        Book book2 = new Book();
        book2.setQuantity(1);

        when(bookRepo.findAll()).thenReturn(Arrays.asList(book1, book2));

        List<Book> books = bookService.getAllExistingBooks();

        Assertions.assertEquals(1, books.size());
        verify(bookRepo, times(1)).findAll();
    }

    @Test
    void shouldUpdateBookPrice() {
        Long id = 1L;
        Double newPrice = 20.0;
        Book book = new Book();

        when(bookRepo.findBookById(id)).thenReturn(book);
        when(bookRepo.save(book)).thenReturn(book);

        bookService.updateBookPrice(id, newPrice);

        Assertions.assertEquals(newPrice, book.getPrice());
        verify(bookRepo, times(1)).findBookById(id);
        verify(bookRepo, times(1)).save(book);
    }

    @Test
    void shouldUpdateBookQuantity() {
        Long id = 1L;
        int newQuantity = 10;
        Book book = new Book();

        when(bookRepo.findBookById(id)).thenReturn(book);
        when(bookRepo.save(book)).thenReturn(book);

        bookService.updateBookQuantity(id, newQuantity);

        Assertions.assertEquals(newQuantity, book.getQuantity());
        verify(bookRepo, times(1)).findBookById(id);
        verify(bookRepo, times(1)).save(book);
    }
}
