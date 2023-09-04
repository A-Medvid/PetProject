package com.petproject.repository;

import com.petproject.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepo extends JpaRepository<Author, Long> {
    Author findByFirstNameAndLastName (String firstName, String lastName);
}
