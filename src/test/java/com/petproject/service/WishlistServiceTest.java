package com.petproject.service;

import com.petproject.entity.Book;
import com.petproject.entity.Person;
import com.petproject.entity.Wishlist;
import com.petproject.repository.WishlistRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class WishlistServiceTest {
    private WishlistRepo wishlistRepo;
    private PersonService personService;
    private WishlistService wishlistService;

    @BeforeEach
    public void setUp() {
        wishlistRepo = Mockito.mock(WishlistRepo.class);
        personService = Mockito.mock(PersonService.class);
        wishlistService = new WishlistService(wishlistRepo, personService);
    }

    @Test
    public void addBookToWishlistWhenWishlistDoesNotExist() {
        Person person = new Person();
        person.setBooksDesired(0);
        Book book = new Book();

        when(wishlistRepo.findByPerson(any(Person.class))).thenReturn(null);

        wishlistService.addBookToWishlist(person, book);

        ArgumentCaptor<Wishlist> wishlistCaptor = ArgumentCaptor.forClass(Wishlist.class);
        verify(wishlistRepo, times(1)).findByPerson(person);
        verify(wishlistRepo, times(1)).save(wishlistCaptor.capture());
        verify(personService, times(1)).updatePerson(any(Person.class));

        Wishlist savedWishlist = wishlistCaptor.getValue();
        assertEquals(person, savedWishlist.getPerson());
        assertTrue(savedWishlist.getBooks().contains(book));
    }

    @Test
    public void addBookToWishlistWhenWishlistExists() {
        Person person = new Person();
        person.setBooksDesired(0);
        Book book = new Book();
        Wishlist wishlist = new Wishlist();
        wishlist.setPerson(person);
        wishlist.setBooks(new ArrayList<>());

        when(wishlistRepo.findByPerson(any(Person.class))).thenReturn(wishlist);

        wishlistService.addBookToWishlist(person, book);

        ArgumentCaptor<Wishlist> wishlistCaptor = ArgumentCaptor.forClass(Wishlist.class);
        verify(wishlistRepo, times(1)).findByPerson(person);
        verify(wishlistRepo, times(1)).save(wishlistCaptor.capture());
        verify(personService, times(1)).updatePerson(any(Person.class));

        Wishlist savedWishlist = wishlistCaptor.getValue();
        assertTrue(savedWishlist.getBooks().contains(book));
        assertEquals(1, person.getBooksDesired());
    }

    @Test
    public void removeBookFromWishlist() {
        Person person = new Person();
        person.setBooksDesired(1);
        Book book = new Book();
        Wishlist wishlist = new Wishlist();
        wishlist.setPerson(person);
        List<Book> books = new ArrayList<>();
        books.add(book);
        wishlist.setBooks(books);

        when(wishlistRepo.findByPerson(any(Person.class))).thenReturn(wishlist);

        wishlistService.removeBookFromWishlist(person, book);

        ArgumentCaptor<Wishlist> wishlistCaptor = ArgumentCaptor.forClass(Wishlist.class);
        verify(wishlistRepo, times(1)).findByPerson(person);
        verify(wishlistRepo, times(1)).save(wishlistCaptor.capture());
        verify(personService, times(1)).updatePerson(any(Person.class));

        Wishlist savedWishlist = wishlistCaptor.getValue();
        assertFalse(savedWishlist.getBooks().contains(book));
        assertEquals(0, person.getBooksDesired());
    }
}

