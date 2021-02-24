package de.oose;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import de.oose.domain.Contract;
import de.oose.domain.Customer;
import de.oose.rest.ContractRepository;
import de.oose.rest.CustomerRepository;

@SpringBootApplication
public class RestApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestApplication.class, args);
	}
	
	@Bean
	CommandLineRunner run(CustomerRepository customerRepository, ContractRepository contractsRepository) {
		return args -> {
			Customer customer = new Customer(null, "John Doe", LocalDate.of(1111, 11, 11));
			customer = customerRepository.save(customer);
			contractsRepository.save(new Contract(null, customer.getId(), "John's First Contract"));
			contractsRepository.save(new Contract(null, customer.getId(), "John's Second Contract"));
			contractsRepository.save(new Contract(null, customer.getId(), "John's Third Contract"));
		};
	}

}
