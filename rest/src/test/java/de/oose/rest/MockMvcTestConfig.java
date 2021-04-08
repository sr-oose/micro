package de.oose.rest;

import static org.mockito.Mockito.*;

import java.time.LocalDate;

import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;

import de.oose.domain.Customer;

@TestConfiguration
public class MockMvcTestConfig {
	
		@Bean
		DiscoveryClient discoveryClient() {
			return(mock(DiscoveryClient.class));
		}

		@Bean
		CustomerRepository customerRepository() {
			var repo = mock(CustomerRepository.class);
			when(repo.save(notNull()))
			  .thenReturn(new Customer(1L, "John Doe", LocalDate.of(1111, 11, 11)));					
			return(repo);
		}

		@Bean
		ContractRepository contractRepository() {
			return(mock(ContractRepository.class));
		}
}
