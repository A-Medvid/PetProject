package com.petproject.service;

import com.petproject.entity.Author;
import com.petproject.repository.AuthorRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class AuthorServiceTest {
    @Mock
    AuthorRepo authorRepo;

    @InjectMocks
    AuthorService authorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldSaveAuthor() {
        Author author = new Author();
        authorService.saveAuthor(author);
        ArgumentCaptor<Author> authorCaptor = ArgumentCaptor.forClass(Author.class);
        verify(authorRepo, times(1)).save(authorCaptor.capture());
        Assertions.assertEquals(author, authorCaptor.getValue());
    }

    @Test
    void shouldFindAuthorByName() {
        String firstName = "TestFirstName";
        String lastName = "TestLastName";
        Author expectedAuthor = new Author();
        expectedAuthor.setFirstName(firstName);
        expectedAuthor.setLastName(lastName);

        when(authorRepo.findByFirstNameAndLastName(firstName, lastName)).thenReturn(expectedAuthor);
        Author resultAuthor = authorService.findByName(firstName, lastName);

        verify(authorRepo, times(1)).findByFirstNameAndLastName(firstName, lastName);
        Assertions.assertEquals(expectedAuthor, resultAuthor);
    }

    @Test
    void shouldHandleNullWhenFindingAuthorByName() {
        String firstName = "TestFirstName";
        String lastName = "TestLastName";

        when(authorRepo.findByFirstNameAndLastName(firstName, lastName)).thenReturn(null);
        Author resultAuthor = authorService.findByName(firstName, lastName);

        verify(authorRepo, times(1)).findByFirstNameAndLastName(firstName, lastName);
        Assertions.assertNull(resultAuthor);
    }
}
