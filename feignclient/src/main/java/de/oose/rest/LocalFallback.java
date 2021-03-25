package de.oose.rest;

import java.time.LocalDate;
import java.util.Collections;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import de.oose.domain.Customer;

public class LocalFallback implements CustomerFeignClient{

	public static final Customer BERND = new Customer("Bernd Stromberg", LocalDate.of(1970, 10, 10));
	
	@Override
	public ResponseEntity<Customer> getCustomer(Long id) {
		return new ResponseEntity<>(BERND, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Iterable<Customer>> getAllCustomers() {
		return new ResponseEntity<>(Collections.singletonList(BERND), HttpStatus.OK);	
    }
	

	@Override
	public ResponseEntity<Long> createCustomer(Customer customer) {
		return new ResponseEntity<>(0L, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Customer> updateCustomer(Long id, Customer customer) {
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<Void> deleteCustomer(Long id) {
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
