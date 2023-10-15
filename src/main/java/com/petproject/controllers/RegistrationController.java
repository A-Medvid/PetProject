package com.petproject.controllers;

import com.petproject.entity.Person;
import com.petproject.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@SessionAttributes({"errorUsernameLength", "errorUsernameExist", "errorEmailValid", "errorEmailExist",
        "errorPassword", "errorPasswordConfirmation", "user"})
public class RegistrationController {

    private final PersonService personService;
    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationController.class);

    @Autowired
    public RegistrationController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addPerson(@Valid Person person,
                            BindingResult bindingResult,
                            Model model, @RequestParam("confirmPassword") String confirmPassword) {
        boolean hasErrors = false;

        if (bindingResult.getFieldError("username") != null) {
            model.addAttribute("errorUsernameLength",
                    "Username length must be between 4 and 16 characters");
            hasErrors = true;
        }
        final Optional<Person> byName = personService.getPersonByName(person.getUsername());
        if (byName.isPresent()) {
            model.addAttribute("errorUsernameExist", "Person with this name already exists");
            hasErrors = true;
        }
        if (bindingResult.getFieldError("email") != null) {
            model.addAttribute("errorEmailValid", "Email is not valid");
            hasErrors = true;
        }
        final Optional<Person> byEmail = personService.getPersonByEmail(person.getEmail());
        if (byEmail.isPresent()) {
            model.addAttribute("errorEmailExist", "Person with this email already exists");
            hasErrors = true;
        }
        if (bindingResult.getFieldError("password") != null) {
            model.addAttribute("errorPassword",
                    "Password length must be between 3 and 16 characters");
            hasErrors = true;
        }
        if (!person.getPassword().equals(confirmPassword)) {
            model.addAttribute("errorPasswordConfirmation", "Passwords do not match");
            hasErrors = true;
        }
        if (hasErrors) {
            model.addAttribute("user", person);
            LOGGER.info("Registration failed for user: {}", person.getUsername());
            return "redirect:/registration";
        }
        personService.saveNewPerson(person);
        LOGGER.info("New user registered successfully: {}", person.getUsername());
        return "redirect:/login";
    }

}