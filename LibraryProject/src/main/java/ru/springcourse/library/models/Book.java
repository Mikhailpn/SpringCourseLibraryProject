package ru.springcourse.library.models;

import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "library.book")
public class Book {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 100, message = "Name should be between 2 and 100 characters")
    @Column
    private String name;

    @NotEmpty(message = "Author should not be empty")
    @Size(min = 2, max = 100, message = "Surname should be between 2 and 100 characters")
    @Column
    private String author;

    @Column
    private int year;

    @ManyToOne()
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Person customer;

    @Column(name = "alloc_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date allocTime;

    @Transient
    private boolean overdue;
    public Book() {

    }

    public Book(int id, String name, String author, int year, Person customer) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.year = year;
        this.customer = customer;
        this.overdue = false;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }


    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Person getCustomer() {
        return customer;
    }

    public void setCustomer(Person customer) {
        this.customer = customer;
    }

    public Date getAllocTime() {
        return allocTime;
    }

    public void setAllocTime(Date allocTime) {
        this.allocTime = allocTime;
    }

    public boolean isOverdue() {
        return overdue;
    }

    public void setOverdue(boolean overdue) {
        this.overdue = overdue;
    }
}
