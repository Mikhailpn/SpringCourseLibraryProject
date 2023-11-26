package ru.springcourse.library.LibraryProject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.springcourse.library.LibraryProject.models.Person;
import ru.springcourse.library.LibraryProject.services.PeopleService;
import ru.springcourse.library.LibraryProject.services.RegistrationService;

import javax.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final PeopleService peopleService;

    private final RegistrationService registrationService;

    public AuthController(PeopleService peopleService, RegistrationService registrationService){
        this.peopleService = peopleService;
        this.registrationService = registrationService;
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

        if (bindingResult.hasErrors())
            return "auth/registration";

        registrationService.register(person);

        return "auth/login";
    }
}
