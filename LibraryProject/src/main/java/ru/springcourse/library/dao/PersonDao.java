package ru.springcourse.library.dao;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.springcourse.library.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDao {

    private final SessionFactory sessionFactory;
    @Autowired
    public PersonDao(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)
    public List<Person> all() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM Person", Person.class).getResultList();
    }

    @Transactional(readOnly = true)
    public Optional<Person> show(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        Optional<Person> person = Optional.of(session.get(Person.class, id));
        if (person.isPresent())
            Hibernate.initialize(person.get().getBookList());

        return person;
    }

    @Transactional
    public void save(Person person) {
        Session session = sessionFactory.getCurrentSession();
        session.save(person);
    }

    @Transactional
    public void update(int id, Person updatedPerson) {
        Session session = sessionFactory.getCurrentSession();
        Person personToUpdate = session.get(Person.class, id);
        personToUpdate.setName(updatedPerson.getName());
        personToUpdate.setBirth_year(updatedPerson.getBirth_year());
        personToUpdate.setSurname(updatedPerson.getSurname());
        personToUpdate.setPatronymic(updatedPerson.getPatronymic());
    }

    @Transactional(readOnly = true)
    public Optional<Person> findByFIO(Person person){
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM Person WHERE name=:name  AND surname=:surname AND patronymic=:patronymic";
        return session.createQuery(hql, Person.class)
                .setParameter("name", person.getName())
                .setParameter("surname", person.getSurname())
                .setParameter("patronymic", person.getPatronymic()).list().stream().findAny();
    }

    @Transactional
    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        Person person = session.get(Person.class, id);
        session.delete(person);
    }


}

