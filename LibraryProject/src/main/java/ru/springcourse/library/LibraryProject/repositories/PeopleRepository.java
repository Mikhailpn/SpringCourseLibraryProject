package ru.springcourse.library.LibraryProject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.springcourse.library.LibraryProject.models.Person;


import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
    Optional<Person> findByNameAndSurnameAndPatronymic(String name, String surname, String patronymic);
    Optional<Person> findByUsername(String login);
}
