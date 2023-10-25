package ru.springcourse.library.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import ru.springcourse.library.models.Person;
import ru.springcourse.library.repositories.PeopleRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {

    private final PeopleRepository peopleRepository;
    @Autowired
    public PeopleService(PeopleRepository peopleRepository){
        this.peopleRepository = peopleRepository;
    }
    public List<Person> findAll(){
        return peopleRepository.findAll();
    }

    public Optional<Person> findById(int id){
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


}
