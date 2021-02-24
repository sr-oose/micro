package de.oose.rest;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import de.oose.domain.Contract;
import de.oose.domain.Customer;

@RefreshScope
@RestController
public class MyRestController {

	private CustomerRepository customerRepository;
	private ContractRepository contractRepository;
	private DiscoveryClient client;
	private String grusswort;

	
	public MyRestController(DiscoveryClient client, CustomerRepository customerRepository, ContractRepository contractRepository) {
		this.client = client;
		this.customerRepository = customerRepository;
		this.contractRepository = contractRepository;
	}

	@Value("${grusswort}")
	public void setGrusswort(String grusswort) {
		this.grusswort = grusswort;
	}
	
	@GetMapping("/hello/{param}")
	public ResponseEntity<String> hello(@PathVariable String param){
		return new ResponseEntity<>(grusswort + " " + param, HttpStatus.OK);
	}
	
	@GetMapping("/instances")
	public ResponseEntity<List<String>> instances() {
		return new ResponseEntity<>(client.getInstances("rest-server").stream().map(e -> e.getUri().toString())
				.collect(Collectors.toList()), HttpStatus.OK);
	}
	
	@GetMapping("/failedmessage")
	public ResponseEntity<String> failedmessage(){
		return new ResponseEntity<>("Target service not available.", HttpStatus.SERVICE_UNAVAILABLE);
	}
	
	@GetMapping("/customers/{id}")
	public ResponseEntity<Customer> getCustomer(@PathVariable("id") Long id){
		var result = customerRepository.findById(id);
		if (result.isPresent()) {
			return new ResponseEntity<Customer>(result.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/customers/{id}/contracts")
	public ResponseEntity<Iterable<Contract>> getCustomerContracts(@PathVariable Long id){
		if (!customerRepository.existsById(id)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		var contracts = contractRepository.findAllByCustomerId(id);
		return new ResponseEntity<>(contracts, HttpStatus.OK);
	}
	
	@PostMapping("/customers/{id}/contracts")
	public ResponseEntity<Contract> addCustomerContract(@PathVariable Long id, @RequestBody Contract contract){
		if (!customerRepository.existsById(id)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		contract.setCustomerId(id);
		contract = contractRepository.save(contract);
		return new ResponseEntity<>(contract, HttpStatus.OK);
	}


	@GetMapping("/customers")
	public ResponseEntity<Iterable<Customer>> getAllCustomers(){
		return new ResponseEntity<Iterable<Customer>>(customerRepository.findAll(), HttpStatus.OK);
	}
	
	@PostMapping(value = "/customers", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Long> createCustomer(@RequestBody Customer customer){
		var savedCustomer = customerRepository.save(customer);
		return new ResponseEntity<Long>(savedCustomer.getId(), HttpStatus.OK);
	}
		
	@PutMapping("/customers/{id}")
	public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer customer){
		var customerOptional = customerRepository.findById(id);
		if (customerOptional.isPresent()) {
			customerOptional.get().setBirthday(customer.getBirthday());
			customerOptional.get().setName(customer.getName());
			customerRepository.save(customerOptional.get());
			return new ResponseEntity<Customer>(customerOptional.get(), HttpStatus.OK);
		} else {
		    return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/customers/{id}")
	public ResponseEntity<Void> deleteCustomer(@PathVariable Long id){
		var customerOptional = customerRepository.findById(id);
		if (customerOptional.isPresent()) {
			customerRepository.delete(customerOptional.get());
			return new ResponseEntity<Void>(HttpStatus.OK);
		} else {
		    return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/customer-hateoas/{id}")
	public ResponseEntity<CustomerRepresentationModel> getCustomerHateoas(@PathVariable("id") Long id) {
		var result = customerRepository.findById(id);
		if (result.isPresent()) {
			return new ResponseEntity<>(new CustomerRepresentationModel(result.get()), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
}
