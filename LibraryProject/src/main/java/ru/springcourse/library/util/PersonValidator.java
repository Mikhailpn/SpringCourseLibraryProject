package ru.springcourse.library.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.springcourse.library.dao.PersonDao;
import ru.springcourse.library.models.Person;

import javax.validation.Valid;
import java.util.Optional;

@Component
public class PersonValidator implements Validator {

    PersonDao personDao;

    public PersonValidator(PersonDao personDao){
        this.personDao = personDao;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        Person person = (Person) target;
        Optional<Person> searchRes = personDao.findByFIO(person);
        if(searchRes.isPresent() && searchRes.get().getId() != person.getId()){
            errors.rejectValue("name", "","This FIO is already in use");
        }

    }
}
