package com.petproject.repository;

import com.petproject.entity.Book;
import com.petproject.entity.Person;
import com.petproject.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RatingRepo extends JpaRepository<Rating, Long> {
    Optional<Rating> findByPersonAndBook(Person person, Book book);
}
