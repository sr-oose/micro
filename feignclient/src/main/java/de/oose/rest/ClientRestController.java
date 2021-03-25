package de.oose.rest;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import de.oose.domain.Customer;

@RestController
public class ClientRestController {

	private CustomerFeignClient customerFeignClient;
	
	public ClientRestController(CustomerFeignClient customerClient) {
		this.customerFeignClient = customerClient;
	}
	
	@GetMapping("/get-customers")
	public ResponseEntity<Iterable<Customer>> getAllCustomers(){
		return customerFeignClient.getAllCustomers();
	}
	
	@PostMapping("/test")
	public ResponseEntity<String> doSomeTesting(){
		StringBuilder builder = new StringBuilder();
		builder.append("Creating customer 1\n");
		
		Customer luke = new Customer("Luke Skywalker", LocalDate.of(2321, 7, 22));
		Customer leia = new Customer("Leia Organa", LocalDate.of(2326, 12, 2));
		
		Long lukesId = customerFeignClient.createCustomer(luke).getBody();
		var customers = customerFeignClient.getAllCustomers().getBody();

		builder.append("List of customers on server:\n");
		customers.forEach(c -> builder.append(c.toString() + "\n"));
		builder.append("\n\nCreating customer 2\n");
		
		Long leiasId = customerFeignClient.createCustomer(leia).getBody();
		customers = customerFeignClient.getAllCustomers().getBody();
		
		builder.append("List of customers on server:\n");
		customers.forEach(c -> builder.append(c.toString() + "\n"));
		builder.append("\n\nUpdating Leia's name\n");	
		
		leia.setName("Leia Organa Solo");
		
		customerFeignClient.updateCustomer(leiasId, leia);
		customers = customerFeignClient.getAllCustomers().getBody();
		
		builder.append("List of customers on server:\n");
		customers.forEach(c -> builder.append(c.toString() + "\n"));
		builder.append("\n\nDeleting Luke\n");
	
		customerFeignClient.deleteCustomer(lukesId);
		customers = customerFeignClient.getAllCustomers().getBody();
		
		builder.append("List of customers on server:\n");
		customers.forEach(c -> builder.append(c.toString() + "\n"));
		
		return new ResponseEntity<String>(builder.toString(), HttpStatus.OK);
	}
}
