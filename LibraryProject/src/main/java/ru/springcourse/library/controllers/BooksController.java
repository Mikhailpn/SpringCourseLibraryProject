package ru.springcourse.library.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.springcourse.library.dao.BookDao;


@Controller
@RequestMapping("/books")
public class BooksController {

    private final BookDao bookDAO;

    @Autowired
    public BooksController(BookDao bookDAO) {
        this.bookDAO = bookDAO;
    }

    @GetMapping()
    public String all(Model model) {
        model.addAttribute("books", bookDAO.all());
        return "books/all";
    }

}