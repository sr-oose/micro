package de.oose.rest;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import de.oose.domain.Customer;

@FeignClient(name = "REST-SERVER" )
public interface CustomerFeignClient {
	
	@GetMapping("/customers/{id}")
	public ResponseEntity<Customer> getCustomer(@PathVariable("id") Long id);

	@GetMapping("/customers")
	public ResponseEntity<Iterable<Customer>> getAllCustomers();
	
	@PostMapping("/customers")
	public ResponseEntity<Long> createCustomer(@RequestBody Customer customer);
	
	@PutMapping("/customers/{id}")
	public ResponseEntity<Customer> updateCustomer(@PathVariable("id") Long id, @RequestBody Customer customer);
	
	@DeleteMapping("/customers/{id}")
	public ResponseEntity<Void> deleteCustomer(@PathVariable("id") Long id);
	
	@GetMapping("/instanceid")
	public ResponseEntity<String> instanceId();
}
