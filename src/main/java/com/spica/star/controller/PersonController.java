package com.spica.star.controller;

import com.spica.star.dto.PersonDto;
import com.spica.star.model.Person;
import com.spica.star.service.PersonService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/persons")
public class PersonController {

    private final ModelMapper modelMapper;
    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService, ModelMapper modelMapper) {
        super();
        this.modelMapper = modelMapper;
        this.personService = personService;
    }

    @GetMapping
    public List<PersonDto> getAllPersons(){
        return personService.getAllPersons()
                .stream()
                .map( person -> modelMapper.map(person, PersonDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonDto> getPersonById(@PathVariable(name = "id") Long id) {
        Person person = personService.getPersonById(id);

        // convert entity to DTO
        PersonDto response = modelMapper.map(person, PersonDto.class);

        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<PersonDto> createPerson(@RequestBody PersonDto personDto) {

        Person personRequest = modelMapper.map(personDto, Person.class);    // convert DTO to entity
        Person person = personService.createPerson(personRequest);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(person.getId())
                .toUri();
        return ResponseEntity.created(location).build();            //Status 201 - CREATED
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonDto> updatePerson(@PathVariable long id, @RequestBody PersonDto personDto) {
        Person personRequest = modelMapper.map(personDto, Person.class);          // convert DTO to Entity
        Person person = personService.updatePerson(id, personRequest);
        PersonDto personResponse = modelMapper.map(person, PersonDto.class);          // entity to DTO

        return ResponseEntity.noContent().build();      //status 200 - Successful
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePerson(@PathVariable(name = "id") Long id) {
        personService.deletePerson(id);
        return ResponseEntity.ok().body("Person id" + 1 + "deleted"); // status 200
    }

}
