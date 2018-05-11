package com.amex.example.rest.controller;

import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.amex.example.rest.model.Person;
import com.amex.example.rest.service.PersonService;


@RestController
@RequestMapping("/people")
public class PersonController {

	//final static Logger logger = Logger.getLogger(PersonController.class);

	@Autowired
	PersonService pService;

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Person> addPerson(@RequestBody Person person) {
		pService.save(person);
		//logger.debug("Added:: " + person);
		return new ResponseEntity<Person>(person, HttpStatus.CREATED);
	}


	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Void> updatePerson(@RequestBody Person person) {
		Person existingPer = pService.getById(person.getId());
		if (existingPer == null) {
			//logger.debug("Person with id " + person.getId() + " does not exists");
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		} else {
			pService.save(person);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
	}


	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Person> getPerson(@PathVariable("id") Long id) {
		Person person = pService.getById(id);
		if (person == null) {
			//logger.debug("Person with id " + id + " does not exists");
			return new ResponseEntity<Person>(HttpStatus.NOT_FOUND);
		}
		//logger.debug("Found Person:: " + person);
		return new ResponseEntity<Person>(person, HttpStatus.OK);
	}


	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Person>> getAllPersons() {
		List<Person> persons = pService.getAll();
		if (persons.isEmpty()) {
			//logger.debug("Persons does not exists");
			return new ResponseEntity<List<Person>>(HttpStatus.NO_CONTENT);
		}
		//logger.debug("Found " + persons.size() + " Persons");
		//logger.debug(Arrays.toString(persons.toArray()));
		return new ResponseEntity<List<Person>>(persons, HttpStatus.OK);
	}


	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deletePerson(@PathVariable("id") Long id) {
		Person pl = pService.getById(id);
		if (pl == null) {
			//logger.debug("Person with id " + id + " does not exists");
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		} else {
			pService.delete(id);
			//logger.debug("Person with id " + id + " deleted");
			return new ResponseEntity<Void>(HttpStatus.GONE);
		}
	}

}
