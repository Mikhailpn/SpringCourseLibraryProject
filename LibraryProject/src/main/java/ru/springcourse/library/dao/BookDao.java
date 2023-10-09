package ru.springcourse.library.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.springcourse.library.models.Book;

import java.util.List;

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

    public List<Book> customerBooks(int id) {
        return jdbcTemplate.query("SELECT * FROM library.book WHERE customer_id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class));
    }

}
