package de.oose.rest;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import de.oose.domain.Customer;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long>{
	List<Customer> findByBirthday(LocalDate birthday);
		
	
}
