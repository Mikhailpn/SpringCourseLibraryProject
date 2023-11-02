package ru.springcourse.library.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.springcourse.library.models.Book;
import ru.springcourse.library.models.Person;
import ru.springcourse.library.services.BookService;
import ru.springcourse.library.services.PeopleService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;


@Controller
@RequestMapping("/books")
public class BooksController {




    private final PeopleService peopleService;

    private final BookService bookService;
    @Autowired
    public BooksController(PeopleService peopleService, BookService bookService) {
        this.peopleService = peopleService;
        this.bookService = bookService;
    }


    @GetMapping()
    public String all(Model model,
                      @RequestParam(name = "page", required = false) Integer page,
                      @RequestParam(name = "books_per_page", required = false) Integer booksPerPage,
                      @RequestParam(name = "sort_by_year", required = false) Boolean sort) {


        model.addAttribute("books", bookService.findAll(page, booksPerPage, sort));
        return "books/all";
    }

    @GetMapping("/{id}")
    public String show(Model model, @ModelAttribute("personAlloc") Person personAlloc, @PathVariable("id") int book_id) {
        Book book = bookService.findById(book_id).get();
        Optional<Person> owner = Optional.ofNullable(book.getCustomer());

        model.addAttribute("book", book);
        if (owner.isPresent())
            model.addAttribute("owner", owner.get());
        else
            model.addAttribute("people", peopleService.findAll());

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
        bookService.create(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String editBook(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", bookService.findById(id).get());
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String updateBook(@ModelAttribute("book") @Valid Book book,
                             BindingResult bindingResult,
                             @PathVariable("id") int id){
        if (bindingResult.hasErrors())
            return "books/edit";
        bookService.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") int id){
        bookService.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/free")
    public String freeBook(@PathVariable("id") int id){
        bookService.free(id);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/allocate")
    public String allocBook(@ModelAttribute("personAlloc") Person personAlloc, @PathVariable("id") int id){
        bookService.allocate(id, personAlloc);
        return "redirect:/books/" + id;
    }

    @GetMapping("/search")
    public String searchBooks(Model model, @RequestParam(name = "beginning", required = false) String beginning){
        return "books/search";
    }

    @PostMapping("/search")
    public String makeSearch(Model model, @RequestParam(name = "beginning", required = false) String beginning){
        if (beginning != null){
            if(beginning.equals(""))
                model.addAttribute("books", Collections.emptyList());
            else
                model.addAttribute("books", bookService.findByNameStartingWith(beginning));
        }
        return "books/search";
    }



}