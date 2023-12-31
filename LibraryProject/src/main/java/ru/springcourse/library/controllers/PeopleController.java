package ru.springcourse.library.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.springcourse.library.dao.BookDao;
import ru.springcourse.library.dao.PersonDao;
import ru.springcourse.library.models.Person;
import ru.springcourse.library.util.PersonValidator;

import javax.validation.Valid;


@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonDao personDao;
    private final BookDao bookDao;

    private final PersonValidator validator;

    @Autowired
    public PeopleController(PersonDao personDao, BookDao bookDao, PersonValidator validator) {

        this.personDao = personDao;
        this.bookDao = bookDao;
        this.validator = validator;
    }

    @GetMapping()
    public String all(Model model) {
        model.addAttribute("people", personDao.all());
        return "people/all";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personDao.show(id).get());
        model.addAttribute("books", bookDao.customerBooks(id));
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

        personDao.save(person);
        return "redirect:/people";
    }

    @PatchMapping("/{id}")
    public String updatePerson(@ModelAttribute("person") @Valid Person person,
                               BindingResult bindingResult,
                               @PathVariable("id") int id) {

        validator.validate(person, bindingResult);

        if (bindingResult.hasErrors())
            return "people/edit";

        personDao.update(id, person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String editPerson(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", personDao.show(id).get());
        return "people/edit";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        personDao.delete(id);
        return "redirect:/people";
    }

}