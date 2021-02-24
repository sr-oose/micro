package de.oose.rest;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.time.LocalDate;

import org.springframework.hateoas.RepresentationModel;

import de.oose.domain.Customer;
import lombok.Getter;

@Getter
public class CustomerRepresentationModel extends RepresentationModel<CustomerRepresentationModel> {
	
	private Long id;
	private String name;
	private LocalDate birthday;

	public CustomerRepresentationModel(Customer customer){
		this.id = customer.getId();
		this.name = customer.getName();
		this.birthday = customer.getBirthday();
		add(linkTo(methodOn(MyRestController.class).getCustomerContracts(id)).withRel("customer-contracts"));
	}
	


}
