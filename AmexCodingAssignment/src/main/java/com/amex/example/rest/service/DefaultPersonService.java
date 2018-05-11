package com.amex.example.rest.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amex.example.rest.model.Person;
import com.amex.example.rest.repository.PersonRepository;

@Service
public class DefaultPersonService implements PersonService {

	@Autowired
	private PersonRepository personRepository;

	@Override
	public Person save(Person entity) {
		return personRepository.save(entity);
	}

	@Override
	public Person getById(Serializable id) {
		return personRepository.getOne((Long) id);
	}

	@Override
	public List<Person> getAll() {
		return personRepository.findAll();
	}

	@Override
	public void delete(Serializable id) {
		personRepository.deleteById((Long) id);
	}

}
