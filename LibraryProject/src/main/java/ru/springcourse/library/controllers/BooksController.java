package ru.springcourse.library.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.springcourse.library.dao.BookDao;
import ru.springcourse.library.dao.PersonDao;
import ru.springcourse.library.models.Book;
import ru.springcourse.library.models.Person;

import javax.validation.Valid;
import java.util.Optional;


@Controller
@RequestMapping("/books")
public class BooksController {

    private final BookDao bookDAO;
    private final PersonDao personDAO;

    @Autowired
    public BooksController(BookDao bookDao, PersonDao personDao) {

        this.bookDAO = bookDao;
        this.personDAO = personDao;
    }

    @GetMapping()
    public String all(Model model) {
        model.addAttribute("books", bookDAO.all());
        return "books/all";
    }

    @GetMapping("/{id}")
    public String show(Model model, @ModelAttribute("personAlloc") Person personAlloc, @PathVariable("id") int id) {
        Book book = bookDAO.show(id).get();
        Optional<Person> owner = personDAO.show(book.getCustomer_id());
        model.addAttribute("book", book);
        if (owner.isPresent())
            model.addAttribute("owner", owner.get());
        else
            model.addAttribute("people", personDAO.all());

        return "books/show";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping()
    public String createbook(@ModelAttribute("book") @Valid Book book,
                             BindingResult bindingResult){

        if (bindingResult.hasErrors())
            return "books/new";
        bookDAO.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String editBook(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", bookDAO.show(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String updateBook(@ModelAttribute("book") @Valid Book book,
                             BindingResult bindingResult,
                             @PathVariable("id") int id){
        if (bindingResult.hasErrors())
            return "books/edit";
        bookDAO.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") int id){
        bookDAO.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/free")
    public String freeBook(@PathVariable("id") int id){
        bookDAO.free(id);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/allocate")
    public String allocBook(@ModelAttribute("personAlloc") Person personAlloc, @PathVariable("id") int id){
        bookDAO.allocate(id, personAlloc.getId());
        return "redirect:/books/" + id;
    }



}