package com.petproject.service;

import com.petproject.entity.Book;
import com.petproject.entity.Person;
import com.petproject.entity.Purchase;
import com.petproject.repository.PurchaseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseService {
    private final PurchaseRepo purchaseRepo;
    private final PersonService personService;
    private final BookService bookService;

    @Autowired
    public PurchaseService(PurchaseRepo purchaseRepo, PersonService personService, BookService bookService) {
        this.purchaseRepo = purchaseRepo;
        this.personService = personService;
        this.bookService = bookService;
    }

    public void savePurchase(Purchase purchase) {
        purchaseRepo.save(purchase);
    }

    /**
     * Used to add book to the cart.
     */
    public void addBookToPurchases(Person person, Book book) {
        Purchase openPurchase = purchaseRepo.findByPersonAndClosed(person, false);
        if (openPurchase == null) {
            openPurchase = new Purchase();
            openPurchase.setPerson(person);
        }
        openPurchase.getBooks().add(book);
        person.setBooksReserved(person.getBooksReserved() + 1);
        book.setQuantity(book.getQuantity() - 1);
        bookService.updateBook(book);
        personService.updatePerson(person);
        purchaseRepo.save(openPurchase);
    }

    /**
     * Used to remove book from the cart.
     */
    public void removeBookFromPurchases(Person person, Book book) {
        Purchase openPurchase = purchaseRepo.findByPersonAndClosed(person, false);
        openPurchase.getBooks().remove(book);
        person.setBooksReserved(person.getBooksReserved() - 1);
        book.setQuantity(book.getQuantity() + 1);
        bookService.updateBook(book);
        personService.updatePerson(person);
        purchaseRepo.save(openPurchase);
    }

    /**
     * Used to close purchase.
     */
    public void checkout(Person person) {
        Purchase openPurchase = purchaseRepo.findByPersonAndClosed(person, false);
        if (openPurchase != null) {
            openPurchase.setClosed(true);
            List<Book> boughtBooks = openPurchase.getBooks();
            for (Book book : boughtBooks) {
                book.setQuantity(book.getQuantity() - 1);
                bookService.updateBook(book);
            }
            person.setBooksReserved(0);
            personService.updatePerson(person);
            purchaseRepo.save(openPurchase);
        }
    }

    /**
     * Used to receive an incomplete purchase.
     */
    public Purchase getOpenPurchaseByPerson(Person person) {
        return purchaseRepo.findByPersonAndClosed(person, false);
    }

    /**
     * Used to receive closed purchases.
     */
    public List<Purchase> getClosedPurchaseByPerson(Person person) {
        return purchaseRepo.findAllByPersonAndClosed(person, true);
    }
}
