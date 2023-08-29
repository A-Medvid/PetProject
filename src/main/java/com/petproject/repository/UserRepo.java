package com.petproject.repository;

import com.petproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);

    Optional<User> findAllByEmail(String email);

    Optional<User> findUserByUsername(String username);
}
