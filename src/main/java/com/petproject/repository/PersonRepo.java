package com.petproject.repository;

import com.petproject.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepo extends JpaRepository<Person, Long> {
    Person findByUsername(String username);

    Optional<Person> findAllByEmail(String email);

    Optional<Person> findPersonByUsername(String username);
}
