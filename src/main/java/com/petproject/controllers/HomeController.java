package com.petproject.controllers;

import com.petproject.entity.Book;
import com.petproject.entity.Person;
import com.petproject.service.BookService;
import com.petproject.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private final BookService bookService;

    @Autowired
    public HomeController(BookService bookService, PersonService personService) {
        this.bookService = bookService;
    }

    @GetMapping("/home")
    public String getHomePage(Model model, @AuthenticationPrincipal Person user) {
        model.addAttribute("user", user);
        Book book = bookService.getRandomBook();
        model.addAttribute("book", book);
        return "home";
    }

    /**
     * Used for creating a person with the ADMIN role. Also check /templates/home.ftlh
     */
/*    @PostMapping("/home")
    public String addAdmin(Person person) {
        personService.setAdminRoleToPerson(person);
        return "redirect:/login";
    }*/
}
