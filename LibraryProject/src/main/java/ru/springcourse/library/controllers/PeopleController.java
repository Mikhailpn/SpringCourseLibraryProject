package ru.springcourse.library.controllers;


import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.springcourse.library.models.Person;
import ru.springcourse.library.services.BookService;
import ru.springcourse.library.services.PeopleService;
import ru.springcourse.library.util.PersonValidator;

import javax.validation.Valid;


@Controller
@RequestMapping("/people")
public class PeopleController {

private SessionFactory sessionFactory;

    private final PersonValidator validator;
    private final PeopleService peopleService;

    private final BookService bookService;


    @Autowired
    public PeopleController(PersonValidator validator, PeopleService peopleService, BookService bookService) {
        this.validator = validator;
        this.peopleService = peopleService;
        this.bookService = bookService;
    }

    @GetMapping()
    public String all(Model model) {
        model.addAttribute("people", peopleService.findAll());
        return "people/all";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        Person person = peopleService.findById(id).get();
        model.addAttribute("person", person);
        model.addAttribute("books", person.getBookList());
        return "people/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    @PostMapping()
    public String createPerson(@ModelAttribute("person") @Valid Person person,
                               BindingResult bindingResult) {

        validator.validate(person, bindingResult);

        if (bindingResult.hasErrors())
            return "people/new";

        peopleService.create(person);
        return "redirect:/people";
    }

    @PatchMapping("/{id}")
    public String updatePerson(@ModelAttribute("person") @Valid Person person,
                               BindingResult bindingResult,
                               @PathVariable("id") int id) {

        validator.validate(person, bindingResult);

        if (bindingResult.hasErrors())
            return "people/edit";

        peopleService.update(id, person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String editPerson(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", peopleService.findById(id).get());
        return "people/edit";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        peopleService.delete(id);
        return "redirect:/people";
    }

}