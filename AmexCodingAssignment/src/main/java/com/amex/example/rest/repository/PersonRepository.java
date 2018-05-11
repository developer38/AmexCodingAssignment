package com.amex.example.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.amex.example.rest.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

}
