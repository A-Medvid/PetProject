package com.petproject.controllers;

import com.petproject.entity.User;
import com.petproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@Controller
@SessionAttributes({"errorUsernameLength", "errorUsernameExist", "errorEmailValid", "errorEmailExist",
        "errorPassword", "errorPasswordConfirmation", "user"})
public class RegistrationController {

    private final UserService userService;

    private final HttpServletRequest request;

    @Autowired
    public RegistrationController(UserService userService, HttpServletRequest request) {
        this.userService = userService;
        this.request = request;
    }


    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser (@Valid User user,
                            BindingResult bindingResult,
                            Model model, @RequestParam("confirmPassword") String confirmPassword) {
        boolean hasErrors = false;
        //TODO обработать ошибки валидации длины строки имени и пароля
        if (bindingResult.getFieldError("username") != null) {
            model.addAttribute("errorUsernameLength",
                    "Username length must be between 4 and 16 characters");
            hasErrors = true;
        }
        final Optional<User> byName = userService.getUserByName(user.getUsername());
        if (byName.isPresent()) {
            model.addAttribute("errorUsernameExist", "User with this name already exists");
            hasErrors = true;
        }
        if (bindingResult.getFieldError("email") != null) {
            model.addAttribute("errorEmailValid", "Email is not valid");
            hasErrors = true;
        }
        final Optional<User> byEmail = userService.getUserByEmail(user.getEmail());
        if (byEmail.isPresent()) {
            model.addAttribute("errorEmailExist", "User with this email already exists");
            hasErrors = true;
        }
        if (bindingResult.getFieldError("password") != null) {
            model.addAttribute("errorPassword",
                    "Password length must be between 3 and 16 characters");
            hasErrors = true;
        }
        if (!user.getPassword().equals(confirmPassword)) {
            model.addAttribute("errorPasswordConfirmation", "Passwords do not match");
            hasErrors = true;
        }
        if (hasErrors) {
            model.addAttribute("user", user);
            return "redirect:/registration";
        }
        userService.saveNewUser(user);
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            request.getSession().invalidate();
        }
        return "redirect:/home";
    }

}