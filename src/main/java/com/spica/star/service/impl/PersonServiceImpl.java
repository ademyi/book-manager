package com.spica.star.service.impl;

import com.spica.star.model.Person;
import com.spica.star.repository.PersonRepository;
import com.spica.star.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository repository;

    @Autowired
    public PersonServiceImpl(PersonRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Person> getAllPersons() {
        return repository.findAll();
    }

    @Override
    public Person createPerson(Person p) {
        return repository.save(p);
    }

    @Override
    public Person updatePerson(long id, Person p) {
        Person result = repository.findById(id)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Person not found"));

        result.setFirstName(p.getFirstName());
        result.setSurname (p.getSurname());
        result.setLoanedList(p.getLoanedList());

        return repository.save(result);
    }

    @Override
    public void deletePerson(long id) {
        Person result = repository.findById(id)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Person not found"));

        repository.delete(result);
    }

    @Override
    public Person getPersonById(long id) {
        Optional<Person> result = repository.findById(id);
        return result.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Person not found"));
    }

    @Override
    public Person getPersonByName(String firstName, String surname) {
        Optional<Person> result = repository.findPersonByFirstNameStartsWithAndSurnameStartsWith(firstName, surname);
        return result.orElse(null);
    }
}
