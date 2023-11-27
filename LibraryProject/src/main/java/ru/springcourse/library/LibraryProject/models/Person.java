package ru.springcourse.library.LibraryProject.models;



import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "person", schema = "library")
public class Person {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Name should not be empty")
    @Size(max = 40, message = "Max length is 40")
    @Column(unique = true)
    private String name;

    @NotEmpty(message = "Surname should not be empty")
    @Size(max = 40, message = "Max length is 40")
    @Column
    private String surname;

    @NotEmpty(message = "Patronymic should not be empty")
    @Size(max = 40, message = "Max length is 40")
    @Column
    private String patronymic;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    private List<Book> bookList;


    @Column(name = "birth_date")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date birthDate;

    @Column(name = "password")
    @NotEmpty(message = "Password should not be empty")
    private String password;
    @Column(name = "login", unique = true)
    @NotEmpty(message = "Login should not be empty")
    private String username;

    @Column(name = "role")
    private String role;

    

    public Person() {

    }

    public Person(int id, String name, String surname, String patronymic, int birth_year, List<Book> bookList) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.bookList = bookList;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id == person.id && Objects.equals(name, person.name) && Objects.equals(surname, person.surname) && Objects.equals(patronymic, person.patronymic) && Objects.equals(bookList, person.bookList) && Objects.equals(birthDate, person.birthDate) && password.equals(person.password) && username.equals(person.username) && Objects.equals(role, person.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, patronymic, bookList, birthDate, password, username, role);
    }
}