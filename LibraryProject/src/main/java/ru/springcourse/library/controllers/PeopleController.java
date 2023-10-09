package ru.springcourse.library.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.springcourse.library.dao.PersonDao;


import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonDao personDAO;

    @Autowired
    public PeopleController(PersonDao personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String all(Model model) {
        model.addAttribute("people", personDAO.all());
        return "people/all";
    }

}