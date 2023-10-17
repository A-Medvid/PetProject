package com.petproject.service;

import com.petproject.entity.Person;
import com.petproject.entity.Purchase;
import com.petproject.repository.PurchaseRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class PurchaseServiceTest {

    @Mock
    PurchaseRepo purchaseRepo;

    @InjectMocks
    PurchaseService purchaseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldSavePurchase() {
        Purchase purchase = new Purchase();
        purchaseService.savePurchase(purchase);
        verify(purchaseRepo, times(1)).save(purchase);
    }

    @Test
    void shouldGetOpenPurchaseByPerson() {
        Person person = new Person();
        Purchase purchase = new Purchase();

        when(purchaseRepo.findByPersonAndClosed(person, false)).thenReturn(purchase);

        Purchase resultPurchase = purchaseService.getOpenPurchaseByPerson(person);

        Assertions.assertEquals(purchase, resultPurchase);
    }
    @Test
    void shouldGetClosedPurchaseByPerson() {
        Person person = new Person();
        List<Purchase> purchases = new ArrayList<>();

        when(purchaseRepo.findAllByPersonAndClosed(person, true)).thenReturn(purchases);

        List<Purchase> resultPurchases = purchaseService.getClosedPurchaseByPerson(person);

        Assertions.assertEquals(purchases, resultPurchases);
    }

}
