package com.amex.example.rest.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Table(name = "person")
public class Person implements Serializable {

	private static final long serialVersionUID = 4250744070371512269L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	
	@Column(name = "name", length = 25)
	private String name;
	
	@Column(name = "age")
	private Integer age;
	
	@Column(name = "dateOfBirth")
	private Date dateOfBirth;
	
	@Column(name = "email", length = 50)
	private String email;

	public Person(){}
	
	public Person(Long id) {
		this.id = id;
	}
	
	public Person(Long id, String name, Integer age, Date dateOfBirth, String email) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.dateOfBirth = dateOfBirth;
		this.email = email;
	}

	public Person(String name, Integer age, Date dateOfBirth, String email) {
		this.name = name;
		this.age = age;
		this.dateOfBirth = dateOfBirth;
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (id == null || obj == null || getClass() != obj.getClass())
			return false;
		Person toCompare = (Person) obj;
		return id.equals(toCompare.id);
	}
	
	@Override
	public int hashCode() {
		return id == null ? 0 : id.hashCode();
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Id: ").append(this.id).append(", Name: ").append(this.name).append(", Age: ")
				.append(this.age).append("DateOfBirth: ").append(this.dateOfBirth).append("Email: ").append(this.email);
		return sb.toString();
	}
}
