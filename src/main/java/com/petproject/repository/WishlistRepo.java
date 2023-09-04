package com.petproject.repository;

import com.petproject.entity.Person;
import com.petproject.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishlistRepo extends JpaRepository<Wishlist, Integer> {
    Wishlist findByPerson(Person person);
}
