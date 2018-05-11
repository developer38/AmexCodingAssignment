package com.amex.example.rest.integrationTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import com.amex.example.rest.model.Person;
import com.amex.example.rest.util.PersonUtils;

/**
 * Integration test for PersonController Initial data gets loaded from
 * init-data.sql in test classpath
 * 
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
@Sql({ "classpath:init-data.sql" })
public class PersonControllerTest {
	
	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void testAddPerson() throws Exception {

		// prepare
		Person person = new Person(PersonUtils.PERSON1_EMAIL, PersonUtils.PERSON1_AGE, new SimpleDateFormat().parse(PersonUtils.PERSON1_DATE_OF_BIRTH), PersonUtils.PERSON1_EMAIL);

		// execute
		ResponseEntity<Person> responseEntity = restTemplate.postForEntity(PersonUtils.URL, person, Person.class);

		// collect Response
		int status = responseEntity.getStatusCodeValue();
		Person resultPerson = responseEntity.getBody();

		// verify
		assertEquals("Incorrect Response Status", HttpStatus.CREATED.value(), status);

		assertNotNull(resultPerson);
		assertNotNull(resultPerson.getId().longValue());

	}

	@Test
	public void testGetPerson() throws Exception {
		// execute
		ResponseEntity<Person> responseEntity = restTemplate.getForEntity(PersonUtils.URL + "{id}", Person.class, new Long(1));

		// collect response
		int status = responseEntity.getStatusCodeValue();
		Person resultPerson = responseEntity.getBody();

		// verify
		assertEquals("Incorrect Response Status", HttpStatus.OK.value(), status);

		assertNotNull(resultPerson);
		assertEquals(1l, resultPerson.getId().longValue());

	}

	@Test
	public void testGetPersonNotExist() throws Exception {

		// execute
		ResponseEntity<Person> responseEntity = restTemplate.getForEntity(PersonUtils.URL + "{id}", Person.class,
				new Long(100));

		// collect response
		int status = responseEntity.getStatusCodeValue();
		Person resultPerson = responseEntity.getBody();

		// verify
		assertEquals("Incorrect Response Status", HttpStatus.NOT_FOUND.value(), status);
		assertNull(resultPerson);
	}

	@Test
	public void testGetAllPerson() throws Exception {
		
		ResponseEntity<List> responseEntity = restTemplate.getForEntity(PersonUtils.URL, List.class);

		// collect response
		int status = responseEntity.getStatusCodeValue();
		List<Person> pListResult = null;
		if (responseEntity.getBody() != null) {
			pListResult = responseEntity.getBody();
		}

		// verify
		assertEquals("Incorrect Response Status", HttpStatus.OK.value(), status);

		assertNotNull("Persons not found", pListResult);
		assertEquals("Incorrect Person List", 1, pListResult.size());

	}

	@Test
	public void testDeletePerson() throws Exception {

		// execute - delete the record added while initializing database with
		// test data

		ResponseEntity<Void> responseEntity = restTemplate.exchange(PersonUtils.URL + "{id}", HttpMethod.DELETE, null, Void.class,
				new Long(1));

		// verify
		int status = responseEntity.getStatusCodeValue();
		assertEquals("Incorrect Response Status", HttpStatus.GONE.value(), status);

	}

	@Test
	public void testUpdatePerson() throws Exception {
		// prepare
		// here the create the person object with ID equal to ID of
		// person need to be updated with updated properties
		Person person = new Person(PersonUtils.PERSON1_ID, PersonUtils.PERSON1_EMAIL, PersonUtils.PERSON1_AGE, new SimpleDateFormat().parse(PersonUtils.PERSON1_DATE_OF_BIRTH), "person1@yahoo.com");
		HttpEntity<Person> requestEntity = new HttpEntity<Person>(person);

		// execute
		ResponseEntity<Void> responseEntity = restTemplate.exchange(PersonUtils.URL, HttpMethod.PUT, requestEntity, Void.class);

		// verify
		int status = responseEntity.getStatusCodeValue();
		assertEquals("Incorrect Response Status", HttpStatus.OK.value(), status);
	}

}
