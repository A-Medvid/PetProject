package com.petproject.controllers;

import com.petproject.entity.Person;
import com.petproject.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
public class UserController {
    private final PersonService personService;
    private final PasswordEncoder passwordEncoder;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(PersonService personService, PasswordEncoder passwordEncoder) {
        this.personService = personService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Returns a profile page.
     */
    @GetMapping("/profile")
    public String getProfilePage(Model model, @AuthenticationPrincipal Person person) {
        model.addAttribute("user", person);
        return "profile";
    }

    /**
     * Used to change the password.
     */
    @PostMapping("/profile")
    public String changePassword(Model model,
                                 @RequestParam("old-password") String oldPassword,
                                 @RequestParam("new-password") String newPassword,
                                 @RequestParam("confirm-password") String confirmPassword,
                                 @AuthenticationPrincipal Person user) {
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            model.addAttribute("errorOldPasswordProfile", "Wrong password");
        } else if (newPassword.trim().isEmpty()) {
            model.addAttribute("errorNewPasswordProfile", "New password can't be empty");
        } else if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("errorPasswordConfirmationProfile", "Passwords do not match");
        } else {
            user.setPassword(passwordEncoder.encode(newPassword));
            personService.updatePerson(user);
            SecurityContextHolder.clearContext();
            LOGGER.info("Password successfully changed for user: " + user.getUsername());
            return "redirect:/login";
        }

        model.addAttribute("user", user);
        LOGGER.info(user.getUsername() + "got issues with changing password");
        return "profile";
    }

}
