package ru.springcourse.library.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.springcourse.library.models.Book;
import ru.springcourse.library.models.Person;
import ru.springcourse.library.repositories.BooksRepository;
import ru.springcourse.library.repositories.PeopleRepository;

import javax.persistence.Table;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {

    private final BooksRepository booksRepository;
    @Autowired
    public BookService(BooksRepository booksRepository){
        this.booksRepository = booksRepository;
    }
    public List<Book> findAll(){
        return booksRepository.findAll();
    }

    public Optional<Book> findById(int id){
        return booksRepository.findById(id);
    }
    @Transactional
    public void create(Book book){
        booksRepository.save(book);
    }
    @Transactional
    public void update(int id, Book book){
        book.setId(id);
        booksRepository.save(book);
    }

    @Transactional
    public void delete(int id){
        booksRepository.deleteById(id);
    }

    @Transactional
    public void free(int id){
        Optional<Book> book = booksRepository.findById(id);
        if (book.isPresent())
            book.get().setCustomer(null);
    }

    @Transactional
    public void allocate(int id, Person customer){
        Optional<Book> book = booksRepository.findById(id);
        if (book.isPresent())
            book.get().setCustomer(customer);
    }


}
