package com.petproject.controllers;

import com.petproject.entity.Person;
import com.petproject.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasAuthority('USER')")
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }


    @GetMapping("profile")
    public String getProfile(Model model, @AuthenticationPrincipal Person person) {
        model.addAttribute("username", person.getUsername());
        model.addAttribute("email", person.getEmail());
        return "profile";
    }

}
