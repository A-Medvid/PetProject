package com.petproject.service;

import com.petproject.entity.Person;
import com.petproject.entity.Role;
import com.petproject.repository.PersonRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.EnumSet;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class PersonServiceTest {
    @Mock
    PersonRepo personRepo;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    PersonService personService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldGetPersonByEmail() {
        String email = "test@example.com";
        Person expectedPerson = new Person();

        when(personRepo.findAllByEmail(email)).thenReturn(Optional.of(expectedPerson));

        Optional<Person> resultPerson = personService.getPersonByEmail(email);

        verify(personRepo, times(1)).findAllByEmail(email);
        Assertions.assertEquals(Optional.of(expectedPerson), resultPerson);
    }

    @Test
    void shouldGetPersonById() {
        Long id = 1L;
        Person expectedPerson = new Person();

        when(personRepo.getById(id)).thenReturn(expectedPerson);

        Person resultPerson = personService.getPersonById(id);

        verify(personRepo, times(1)).getById(id);
        Assertions.assertEquals(expectedPerson, resultPerson);
    }

    @Test
    void shouldSavePerson() {
        Person person = new Person();
        person.setPassword("password");

        String encodedPassword = "encodedPassword";
        when(passwordEncoder.encode(person.getPassword())).thenReturn(encodedPassword);

        personService.saveNewPerson(person);

        ArgumentCaptor<Person> personCaptor = ArgumentCaptor.forClass(Person.class);
        verify(personRepo, times(1)).save(personCaptor.capture());

        Person savedPerson = personCaptor.getValue();
        Assertions.assertEquals(encodedPassword, savedPerson.getPassword());
        Assertions.assertTrue(savedPerson.getRoles().contains(Role.USER));
    }

    @Test
    void shouldUpdatePerson() {
        Person person = new Person();

        personService.updatePerson(person);

        verify(personRepo, times(1)).save(person);
    }

    @Test
    void shouldSetAdminRoleToPerson() {
        Person person = new Person();

        personService.setAdminRoleToPerson(person);

        ArgumentCaptor<Person> personCaptor = ArgumentCaptor.forClass(Person.class);
        verify(personRepo, times(1)).save(personCaptor.capture());

        Person updatedPerson = personCaptor.getValue();
        Assertions.assertTrue(updatedPerson.getRoles().containsAll(EnumSet.of(Role.ADMIN, Role.USER)));
    }

    @Test
    void shouldLoadUserByUsername() {
        String username = "username";
        Person expectedPerson = new Person();
        expectedPerson.setUsername(username);

        when(personRepo.findByUsername(username)).thenReturn(expectedPerson);

        UserDetails resultUserDetails = personService.loadUserByUsername(username);

        verify(personRepo, times(1)).findByUsername(username);
        Assertions.assertEquals(expectedPerson, resultUserDetails);
    }

    @Test
    void shouldThrowUsernameNotFoundException() {
        String username = "username";

        when(personRepo.findByUsername(username)).thenReturn(null);

        Assertions.assertThrows(UsernameNotFoundException.class, () -> {
            personService.loadUserByUsername(username);
        });

        verify(personRepo, times(1)).findByUsername(username);
    }
}

