package ru.springcourse.library.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Person {
    private int id;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 40, message = "Name should be between 2 and 30 characters")
    private String name;

    @NotEmpty(message = "Surname should not be empty")
    @Size(min = 2, max = 40, message = "Surname should be between 2 and 40 characters")
    private String surname;

    @Size(min = 2, max = 40, message = "Patronymic should be between 2 and 40 characters")
    private String patronymic;


    private int birth_year;



    public Person() {

    }

    public Person(int id, String name, String surname, String patronymic, int birth_year) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.birth_year = birth_year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public int getBirth_year() {
        return birth_year;
    }

    public void setBirth_year(int birth_year) {
        this.birth_year = birth_year;
    }
}