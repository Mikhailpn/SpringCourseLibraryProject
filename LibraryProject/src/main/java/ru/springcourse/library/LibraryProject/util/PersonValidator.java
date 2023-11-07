package ru.springcourse.library.LibraryProject.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.springcourse.library.LibraryProject.models.Person;
import ru.springcourse.library.LibraryProject.services.PeopleService;


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
