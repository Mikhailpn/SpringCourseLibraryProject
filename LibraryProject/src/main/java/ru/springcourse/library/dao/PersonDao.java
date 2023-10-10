package ru.springcourse.library.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.springcourse.library.models.Person;

import java.util.List;
import java.util.Optional;

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

    public Optional<Person> show(Integer id) {
        return jdbcTemplate.query("SELECT * FROM library.person WHERE id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny();
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO library.person(name, surname, patronymic, birth_year) VALUES(?, ?, ?, ?)", person.getName(), person.getSurname(), person.getPatronymic(), person.getBirth_year());
    }

    public void update(int id, Person person) {
        jdbcTemplate.update("UPDATE library.person SET name = ?, surname = ?, patronymic = ?, birth_year = ? WHERE id = ?",
                person.getName(),
                person.getSurname(),
                person.getPatronymic(),
                person.getBirth_year(),
                id);
    }

    public Optional<Person> findByFIO(Person person){
        return jdbcTemplate.query("SELECT * FROM library.person WHERE name = ? AND surname = ? AND patronymic = ?",
                new Object[]{person.getName(), person.getSurname(), person.getPatronymic()},
                new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny();
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM library.person  WHERE id = ?", id);
    }


}

