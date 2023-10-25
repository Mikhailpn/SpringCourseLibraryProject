package ru.springcourse.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.springcourse.library.models.Book;

import javax.persistence.criteria.CriteriaBuilder;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {


}