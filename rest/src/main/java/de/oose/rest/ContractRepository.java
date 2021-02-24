package de.oose.rest;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import de.oose.domain.Contract;

@Repository
public interface ContractRepository extends CrudRepository<Contract, Long>{

	List<Contract> findAllByCustomerId(Long customerId);
}
