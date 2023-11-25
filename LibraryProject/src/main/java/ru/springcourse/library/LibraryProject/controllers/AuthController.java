package ru.springcourse.library.LibraryProject.controllers;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.springcourse.library.LibraryProject.models.Person;
import ru.springcourse.library.LibraryProject.services.PeopleService;
import ru.springcourse.library.LibraryProject.util.PersonValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final PersonValidator personValidator;
    private final PeopleService peopleService;

    private final PasswordEncoder passwordEncoder;

    public AuthController(PeopleService peopleService, PersonValidator personValidator, PasswordEncoder passwordEncoder){
        this.peopleService = peopleService;
        this.personValidator = personValidator;
        this.passwordEncoder = passwordEncoder;
    }
    @GetMapping("/login")
    public String loginPage(@ModelAttribute Person person){
        return "auth/login";
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute Person person){
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String registrationPerform(@ModelAttribute @Valid Person person, BindingResult bindingResult){

        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors())
            return "auth/registration";

        person.setPassword(passwordEncoder.encode(person.getPassword()));
        peopleService.create(person);

        return "auth/login";
    }
}
