package com.petproject.controllers;

import com.petproject.entity.Person;
import com.petproject.service.PersonService;
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
            user.setPassword(newPassword);
            personService.updatePerson(user);
            SecurityContextHolder.clearContext();
            return "redirect:/login";
        }

        model.addAttribute("user", user);
        return "profile";
    }

}
