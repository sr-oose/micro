package de.oose.domain;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Customer {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String name;

	private LocalDate birthday;

	public Customer(Long id, String name, LocalDate birthday) {
		this.id = id;
		this.name = name;
		this.birthday = birthday;
	}

	public Customer() {
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

	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

}
