package ru.springcourse.library.services;


import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import ru.springcourse.library.models.Book;
import ru.springcourse.library.models.Person;
import ru.springcourse.library.repositories.PeopleRepository;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;


@Service
@Transactional(readOnly = true)
public class PeopleService {

    //просрочка книги, измеряется в днях
    private final int overdue = 10;
    private final PeopleRepository peopleRepository;
    @Autowired
    public PeopleService(PeopleRepository peopleRepository){
        this.peopleRepository = peopleRepository;
    }
    public List<Person> findAll(){
        return peopleRepository.findAll();
    }

    public Optional<Person> findById(int id){
        Optional<Person> person = peopleRepository.findById(id);
        if(person.isPresent()) {
            Hibernate.initialize(person.get().getBookList());
            person.get().getBookList().forEach(x->x.setOverdue(checkOverdue(x)));
        }
        return peopleRepository.findById(id);
    }
    @Transactional
    public void create(Person person){
        peopleRepository.save(person);
    }
    @Transactional
    public void update(int id, Person person){
        person.setId(id);
        peopleRepository.save(person);
    }

    @Transactional
    public void delete(int id){
        peopleRepository.deleteById(id);
    }

    public List<Person> findByFIO(Person person){
        return peopleRepository.findByNameAndSurnameAndPatronymic(person.getName(), person.getSurname(), person.getPatronymic());
    }

    private boolean checkOverdue(Book book){

        //книга свободна
        if (book.getAllocTime() == null)
            return false;

        Calendar curDateCalendar = new GregorianCalendar();
        Calendar allocDateCalendar = new GregorianCalendar();
        allocDateCalendar.setTime(book.getAllocTime());

        //прибавляем просрочку к дате выдаче, если текущая дата больше, то книга просрочена
        allocDateCalendar.add(Calendar.DAY_OF_MONTH, overdue);

        if (curDateCalendar.after(allocDateCalendar))
            return true;
        else
            return false;

    }


}
