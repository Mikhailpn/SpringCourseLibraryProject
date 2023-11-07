package ru.springcourse.library.LibraryProject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LibraryController {
    @GetMapping()
    public String main() {
        return "main";
    }

}