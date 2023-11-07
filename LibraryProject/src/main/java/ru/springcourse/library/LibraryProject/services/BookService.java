package ru.springcourse.library.LibraryProject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.springcourse.library.LibraryProject.models.Book;
import ru.springcourse.library.LibraryProject.models.Person;
import ru.springcourse.library.LibraryProject.repositories.BooksRepository;


import java.util.Date;
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
    public List<Book> findAll(Integer page, Integer booksPerPage, Boolean sortByYear){

        //запрос с пагинацией
        if (page != null && booksPerPage != null) {
            Pageable pageRequest;
            if (sortByYear != null)
                pageRequest = PageRequest.of(page, booksPerPage, Sort.by("year"));
            else
                pageRequest = PageRequest.of(page, booksPerPage);

            return booksRepository.findAll(pageRequest).toList();
        }

        //Запрос без пагинации
        if (sortByYear != null)
            return booksRepository.findAll(Sort.by("year"));

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
        Optional<Book> bookToUpdate =  booksRepository.findById(id);
        if (bookToUpdate.isPresent()){
            book.setId(id);
            book.setCustomer(bookToUpdate.get().getCustomer());
            booksRepository.save(book);
        }
    }

    @Transactional
    public void delete(int id){
        booksRepository.deleteById(id);
    }

    @Transactional
    public void free(int id){
        Optional<Book> book = booksRepository.findById(id);
        if (book.isPresent()) {
            book.get().setCustomer(null);
            book.get().setAllocTime(null);
        }
    }

    @Transactional
    public void allocate(int id, Person customer){
        Optional<Book> book = booksRepository.findById(id);
        if (book.isPresent()) {
            book.get().setCustomer(customer);
            book.get().setAllocTime(new Date());
        }
    }

    public List<Book> findByNameStartingWith(String beginning){
        return booksRepository.findByNameStartingWith(beginning);
    }



}
