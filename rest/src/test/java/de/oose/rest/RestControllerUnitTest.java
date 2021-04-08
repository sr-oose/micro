package de.oose.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;

import de.oose.domain.Customer;

public class RestControllerUnitTest {

	private MyRestController controller;

	@Mock
	private CustomerRepository customerRepo;

	@Mock
	private ContractRepository contractRepo;

	@Mock
	private DiscoveryClient discoveryClient;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
		controller = new MyRestController(discoveryClient, customerRepo, contractRepo);
	}

	@Test
	public void updatingExistingCustomerWorksCorrectly() {
		// Arrange
		Customer oldCustomer = new Customer(23L, "Anakin Skywalker", LocalDate.of(2222, 02, 02));
		Customer newCustomer = new Customer(17L, "Darth Vader", LocalDate.of(2252, 12, 03));
		when(customerRepo.findById(oldCustomer.getId())).thenReturn(Optional.of(oldCustomer));

		// Act
		var result = controller.updateCustomer(oldCustomer.getId(), newCustomer);

		// Assert
		assertEquals(HttpStatus.OK, result.getStatusCode());
		Customer body = result.getBody();
		assertEquals(newCustomer.getBirthday(), body.getBirthday());
		assertEquals(newCustomer.getName(), body.getName());
		assertEquals(oldCustomer.getId(), body.getId());
	}

	@Test
	public void updatingNonExistingCustomerFails() {
		// Arrange
		Customer oldCustomer = new Customer(23L, "Anakin Skywalker", LocalDate.of(2222, 02, 02));
		Customer newCustomer = new Customer(17L, "Darth Vader", LocalDate.of(2252, 12, 03));
		when(customerRepo.findById(anyLong())).thenReturn(Optional.empty());

		// Act
		var result = controller.updateCustomer(oldCustomer.getId(), newCustomer);

		// Assert
		assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
	}

}
