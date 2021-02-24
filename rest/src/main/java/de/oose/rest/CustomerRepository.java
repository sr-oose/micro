package de.oose.rest;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import de.oose.domain.Customer;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long>{

}
