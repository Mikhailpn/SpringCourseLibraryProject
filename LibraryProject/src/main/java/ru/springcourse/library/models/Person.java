package ru.springcourse.library.models;
import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "library.person")
public class Person {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Name should not be empty")
    @Size(max = 40, message = "Max length is 40")
    @Column
    private String name;

    @NotEmpty(message = "Surname should not be empty")
    @Size(max = 40, message = "Max length is 40")
    @Column
    private String surname;

    @NotEmpty(message = "Patronymic should not be empty")
    @Size(max = 40, message = "Max length is 40")
    @Column
    private String patronymic;


    @Min(value = 1900, message = "Year must be > 1900")
    @Column
    private int birth_year;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "customer")
    private List<Book> bookList;


    public Person() {

    }

    public Person(int id, String name, String surname, String patronymic, int birth_year, List<Book> bookList) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.birth_year = birth_year;
        this.bookList = bookList;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }
}