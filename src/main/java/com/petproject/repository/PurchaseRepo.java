package com.petproject.repository;

import com.petproject.entity.Purchase;
import com.petproject.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseRepo extends JpaRepository<Purchase, Integer> {
    Purchase findByPersonAndClosed(Person person, boolean closed);

    List<Purchase> findAllByPersonAndClosed(Person person, boolean closed);
}
