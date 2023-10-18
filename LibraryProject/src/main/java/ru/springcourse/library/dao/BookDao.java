package ru.springcourse.library.dao;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.springcourse.library.models.Book;
import ru.springcourse.library.models.Person;


import java.util.List;
import java.util.Optional;

@Component
public class BookDao {

    //private final JdbcTemplate jdbcTemplate;
    private final SessionFactory sessionFactory;

    @Autowired
    public BookDao(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)
    public List<Book> all() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM Book", Book.class).getResultList();
    }

    @Transactional(readOnly = true)
    public Optional<Book> show(int id) {
        Session session = sessionFactory.getCurrentSession();
        Optional<Book> book = Optional.of(session.get(Book.class, id));
        if (book.isPresent())
            Hibernate.initialize(book.get().getPersonList());
        return book;
    }

    @Transactional(readOnly = true)
    public List<Book> customerBooks(int id) {
        Session session = sessionFactory.getCurrentSession();
        Person person = session.get(Person.class, id);
        return person.getBookList();
    }
    @Transactional()
    public void save(Book book) {
        Session session = sessionFactory.getCurrentSession();
        session.save(book);
    }

    @Transactional()
    public void update(int id, Book updatedBook) {
        Session session = sessionFactory.getCurrentSession();
        Book bookToUpdate = session.get(Book.class, id);
        bookToUpdate.setAuthor(updatedBook.getAuthor());
        bookToUpdate.setName(updatedBook.getName());
        bookToUpdate.setYear(updatedBook.getYear());
    }

    @Transactional
    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class, id);
        session.delete(book);
    }

    @Transactional()
    public void free(int book_id, Person personToFree){
        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class, book_id);
        Person person = session.get(Person.class, personToFree.getId());
        book.getPersonList().remove(person);
        book.getPersonList();
    }

    @Transactional
    public void allocate(int book_id, int customer_id){
        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class, book_id);
        Person customer = session.get(Person.class, customer_id);
        book.getPersonList().add(customer);
    }

}
