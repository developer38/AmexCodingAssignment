package com.amex.example.rest.unitTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.amex.example.rest.model.Person;
import com.amex.example.rest.service.PersonService;
import com.amex.example.rest.util.PersonUtils;
import com.google.gson.reflect.TypeToken;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PersonControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	PersonService pService;
	
	@Test
	public void testAddPerson() throws Exception {

		// prepare data and mock's behaviour
		Person pStub = new Person(PersonUtils.PERSON1_NAME, PersonUtils.PERSON1_AGE, new SimpleDateFormat().parse(PersonUtils.PERSON1_DATE_OF_BIRTH), PersonUtils.PERSON1_EMAIL);
		when(pService.save(any(Person.class))).thenReturn(pStub);

		// execute
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(PersonUtils.URL).contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaType.APPLICATION_JSON_UTF8).content(TestUtils.objectToJson(pStub))).andReturn();

		// verify
		int status = result.getResponse().getStatus();
		assertEquals("Incorrect Response Status", HttpStatus.CREATED.value(), status);

		// verify that service method was called once
		verify(pService).save(any(Person.class));

		Person resultPerson = TestUtils.jsonToObject(result.getResponse().getContentAsString(), Person.class);
		assertNotNull(resultPerson);
		assertEquals(1l, resultPerson.getId().longValue());

	}

	@Test
	public void testGetPerson() throws Exception {

		// prepare data and mock's behaviour
		Person pStub = new Person(PersonUtils.PERSON1_ID, PersonUtils.PERSON1_NAME, PersonUtils.PERSON1_AGE, new SimpleDateFormat().parse(PersonUtils.PERSON1_DATE_OF_BIRTH), PersonUtils.PERSON1_EMAIL);
		when(pService.getById(any(Long.class))).thenReturn(pStub);

		// execute
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.get(PersonUtils.URL + "{id}", new Long(1)).accept(MediaType.APPLICATION_JSON_UTF8))
				.andReturn();

		// verify
		int status = result.getResponse().getStatus();
		assertEquals("Incorrect Response Status", HttpStatus.OK.value(), status);

		// verify that service method was called once
		verify(pService).getById(any(Long.class));

		Person resultPerson = TestUtils.jsonToObject(result.getResponse().getContentAsString(), Person.class);
		assertNotNull(resultPerson);
		assertEquals(1l, resultPerson.getId().longValue());
	}

	@Test
	public void testGetPersonNotExist() throws Exception {

		// prepare data and mock's behaviour
		// Not Required as people Not Exist scenario

		// execute
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.get(PersonUtils.URL + "{id}", new Long(1)).accept(MediaType.APPLICATION_JSON_UTF8))
				.andReturn();

		// verify
		int status = result.getResponse().getStatus();
		assertEquals("Incorrect Response Status", HttpStatus.NOT_FOUND.value(), status);

		// verify that service method was called once
		verify(pService).getById(any(Long.class));

		Person resultPerson = TestUtils.jsonToObject(result.getResponse().getContentAsString(), Person.class);
		assertNull(resultPerson);
	}

	@Test
	public void testGetAllPerson() throws Exception {

		// prepare data and mock's behaviour
		List<Person> pList = buildPersons();
		when(pService.getAll()).thenReturn(pList);

		// execute
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(PersonUtils.URL).accept(MediaType.APPLICATION_JSON_UTF8))
				.andReturn();

		// verify
		int status = result.getResponse().getStatus();
		assertEquals("Incorrect Response Status", HttpStatus.OK.value(), status);

		// verify that service method was called once
		verify(pService).getAll();

		// get the List<Person> from the Json response
		TypeToken<List<Person>> token = new TypeToken<List<Person>>() {};
		@SuppressWarnings("unchecked")
		List<Person> pListResult = TestUtils.jsonToList(result.getResponse().getContentAsString(), token);

		assertNotNull("Persons not found", pListResult);
		assertEquals("Incorrect Person List", pList.size(), pListResult.size());

	}

	@Test
	public void testDeletePerson() throws Exception {
		
		// prepare data and mock's behaviour
		Person pStub = new Person(1l);
		when(pService.getById(any(Long.class))).thenReturn(pStub);

		// execute
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete(PersonUtils.URL + "{id}", new Long(1))).andReturn();

		// verify
		int status = result.getResponse().getStatus();
		assertEquals("Incorrect Response Status", HttpStatus.GONE.value(), status);

		// verify that service method was called once
		verify(pService).delete(any(Long.class));

	}

	@Test
	public void testUpdatePerson() throws Exception {
		// prepare data and mock's behaviour
		// here the stub is the updated people object with ID equal to ID of
		// people need to be updated
		Person pStub = new Person(PersonUtils.PERSON1_ID, PersonUtils.PERSON1_NAME, PersonUtils.PERSON1_AGE, new SimpleDateFormat().parse(PersonUtils.PERSON1_DATE_OF_BIRTH), "person1@yahoo.com");
		when(pService.getById(any(Long.class))).thenReturn(pStub);

		// execute
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put(PersonUtils.URL).contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaType.APPLICATION_JSON_UTF8).content(TestUtils.objectToJson(pStub))).andReturn();

		// verify
		int status = result.getResponse().getStatus();
		assertEquals("Incorrect Response Status", HttpStatus.OK.value(), status);

		// verify that service method was called once
		verify(pService).save(any(Person.class));

	}

	private List<Person> buildPersons() throws ParseException {
		Person p1 = new Person(PersonUtils.PERSON1_ID, PersonUtils.PERSON1_NAME, PersonUtils.PERSON1_AGE, new SimpleDateFormat().parse(PersonUtils.PERSON1_DATE_OF_BIRTH), PersonUtils.PERSON1_EMAIL);
		Person p2 = new Person(PersonUtils.PERSON2_ID, PersonUtils.PERSON2_NAME, PersonUtils.PERSON2_AGE, new SimpleDateFormat().parse(PersonUtils.PERSON2_DATE_OF_BIRTH), PersonUtils.PERSON2_EMAIL);
		List<Person> listOfPerson = Arrays.asList(p1, p2);
		return listOfPerson;
	}

}
