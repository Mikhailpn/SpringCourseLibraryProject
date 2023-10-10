package ru.springcourse.library.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.springcourse.library.models.Book;
import ru.springcourse.library.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class BookDao {

    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public BookDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> all() {
        return jdbcTemplate.query("SELECT * FROM library.book", new BeanPropertyRowMapper<>(Book.class));
    }

    public Optional<Book> show(int id) {
        return jdbcTemplate.query("SELECT * FROM library.book WHERE id = ?",
                new Object[]{id},
                new BeanPropertyRowMapper<>(Book.class)).
                stream().findAny();
    }
    public List<Book> customerBooks(int id) {
        return jdbcTemplate.query("SELECT * FROM library.book WHERE customer_id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class));
    }

    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO library.book(name, author, year) VALUES(?, ?, ?)",
                book.getName(),
                book.getAuthor(),
                book.getYear());
    }

    public void update(int id, Book book) {
        jdbcTemplate.update("UPDATE library.book SET name = ?, author = ?, year = ? WHERE id = ?",
                book.getName(),
                book.getAuthor(),
                book.getYear(),
                id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM library.book WHERE id = ?", id);
    }

    public void free(int id){
        jdbcTemplate.update("UPDATE library.book SET customer_id = NULL WHERE id = ?", id);
    }

    public void allocate(int book_id, Integer customer_id){
        jdbcTemplate.update("UPDATE library.book SET customer_id = ? WHERE id = ?",customer_id, book_id);
    }

}
