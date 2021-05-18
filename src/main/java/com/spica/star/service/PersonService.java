package com.spica.star.service;

import com.spica.star.model.Person;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PersonService {
    List<Person> getAllPersons();
    Person createPerson(Person p);
    Person updatePerson(long id, Person p);
    void deletePerson(long id);
    Person getPersonById(long id);
    Person getPersonByName(String firstName, String surname);
}
