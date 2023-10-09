package ru.springcourse.library.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.springcourse.library.models.Person;

import java.util.List;

@Component
public class PersonDao {

    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public PersonDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> all() {
        return jdbcTemplate.query("SELECT * FROM library.person", new BeanPropertyRowMapper<>(Person.class));
    }


}

