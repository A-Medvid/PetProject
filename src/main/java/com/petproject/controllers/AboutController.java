package com.petproject.controllers;

import com.petproject.entity.About;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/about")
public class AboutController {

    /**
     * Returns information about the creator of the project. Used in footer.
     */
    @GetMapping
    public About getAbout() {
        About about = new About();
        about.setCreator("Arsen Medvid");
        about.setDescription("This pet-project is created as a result of my study as a Java Backend-developer " +
                "at Study Space school and demonstrates the experience I’ve gained.");
        about.setContacts("Contact me on Telegram - @arsen_igorevich");

        return about;
    }
}
