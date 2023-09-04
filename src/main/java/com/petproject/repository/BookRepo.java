package com.petproject.repository;

import com.petproject.entity.Book;
import com.petproject.entity.Genre;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepo extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
    @Override
    List<Book> findAll(Sort sort);

    List<Book> findAll(Specification specification, Sort sort);

    Book findBookById(Long id);

    List<Book> findAllByGenre(Genre genre);
}
