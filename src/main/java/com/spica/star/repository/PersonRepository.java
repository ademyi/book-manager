package com.spica.star.repository;

import com.spica.star.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<com.spica.star.model.Person, Long> {
    Optional<Person> findPersonByFirstNameStartsWithAndSurnameStartsWith(String firstName, String surname);
}
