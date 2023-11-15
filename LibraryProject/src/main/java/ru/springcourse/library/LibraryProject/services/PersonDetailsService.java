package ru.springcourse.library.LibraryProject.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.springcourse.library.LibraryProject.models.Person;
import ru.springcourse.library.LibraryProject.repositories.PeopleRepository;
import ru.springcourse.library.LibraryProject.security.PersonDetails;

import java.util.Optional;

@Service
public class PersonDetailsService implements UserDetailsService {

    private final PeopleRepository peopleRepository;

    public PersonDetailsService(PeopleRepository peopleRepository){
        this.peopleRepository = peopleRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        String[] fio = s.split("\\|");
        Optional<Person> person = peopleRepository.findByNameAndSurnameAndPatronymic(fio[0],fio[1], fio[2]);

        if (person.isEmpty()){
            throw new UsernameNotFoundException("User not found");
        }

        return new PersonDetails(person.get());
    }
}
