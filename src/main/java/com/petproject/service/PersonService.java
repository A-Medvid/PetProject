package com.petproject.service;

import com.petproject.entity.Person;
import com.petproject.entity.Role;
import com.petproject.repository.PersonRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Optional;

@Service
public class PersonService implements UserDetailsService {

    private final PersonRepo personRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PersonService(PersonRepo personRepo, PasswordEncoder passwordEncoder) {
        this.personRepo = personRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person person = personRepo.findByUsername(username);

        if (person == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return person;
    }

    /**
     * Used to save new user to the database.
     */
    public void saveNewPerson(Person person) {
        person.setRoles(Collections.singleton(Role.USER));
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        personRepo.save(person);
    }

    public Person getPersonById(Long id) {
        return personRepo.getById(id);
    }

    public Optional<Person> getPersonByEmail(final String email) {
        return personRepo.findAllByEmail(email);
    }

    public Optional<Person> getPersonByName(final String name) {
        return personRepo.findPersonByUsername(name);
    }

    /**
     * Used to update the data of an existing user.
     */
    public void updatePerson(final Person person) {
        personRepo.save(person);
    }

    public void setAdminRoleToPerson(final Person person) {
        person.setRoles(EnumSet.of(Role.ADMIN, Role.USER));
        personRepo.save(person);
    }
}
