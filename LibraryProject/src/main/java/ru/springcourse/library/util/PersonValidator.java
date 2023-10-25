package ru.springcourse.library.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.springcourse.library.models.Person;
import ru.springcourse.library.services.PeopleService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Component
public class PersonValidator implements Validator {

    PeopleService peopleService;

    public PersonValidator(PeopleService peopleService){
        this.peopleService = peopleService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        Person person = (Person) target;
        Optional<Person> searchRes = peopleService.findByFIO(person).stream().findAny();
        if(searchRes.isPresent() && searchRes.get().getId() != person.getId()){
            errors.rejectValue("name", "","This FIO is already in use");
        }

    }
}
