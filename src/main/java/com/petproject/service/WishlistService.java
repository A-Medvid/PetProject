package com.petproject.service;

import com.petproject.entity.Book;
import com.petproject.entity.Person;
import com.petproject.entity.Wishlist;
import com.petproject.repository.WishlistRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WishlistService {
    private final WishlistRepo wishlistRepo;
    private final PersonService personService;

    @Autowired
    public WishlistService(WishlistRepo wishlistRepo, PersonService personService) {
        this.wishlistRepo = wishlistRepo;
        this.personService = personService;
    }

    public void saveWishlist(Wishlist wishlist) {
        wishlistRepo.save(wishlist);
    }

    /**
     * Used to add a book to wishlist.
     */
    public void addBookToWishlist(Person person, Book book) {

        Wishlist wishlist = wishlistRepo.findByPerson(person);

        if (wishlist == null) {
            wishlist = new Wishlist();
            wishlist.setPerson(person);
        }
        wishlist.getBooks().add(book);
        person.setBooksDesired(person.getBooksDesired() + 1);
        personService.updatePerson(person);
        wishlistRepo.save(wishlist);
    }

    /**
     * Used to remove book from wishlist.
     */
    public void removeBookFromWishlist(Person person, Book book) {
        Wishlist wishlist = wishlistRepo.findByPerson(person);
        wishlist.getBooks().remove(book);
        person.setBooksDesired(person.getBooksDesired() - 1);
        personService.updatePerson(person);
        wishlistRepo.save(wishlist);
    }

    /**
     * Used to get user's wishlist.
     */
    public Wishlist getWishlistByPerson(Person person) {
        return wishlistRepo.findByPerson(person);
    }
}
