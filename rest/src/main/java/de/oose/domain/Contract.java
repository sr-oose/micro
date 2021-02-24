package de.oose.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Contract {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private Long customerId;
	
	private String description;

	public Contract(Long id, Long customerId, String description) {
		this.id = id;
		this.customerId = customerId;
		this.description = description;
	}

	public Contract() {
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customer) {
		this.customerId = customer;
	}

}
