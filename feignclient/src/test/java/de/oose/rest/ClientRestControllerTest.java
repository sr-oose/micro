package de.oose.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import com.github.tomakehurst.wiremock.client.WireMock;

import de.oose.domain.Customer;

@SpringBootTest
@ActiveProfiles("test")
@Import(LoadBalancerConfig.class)
@AutoConfigureWireMock(port = 8080)
public class ClientRestControllerTest {
	
	@Autowired
	private ClientRestController controller;
	
	@AfterEach
	public void reset() {
		WireMock.reset();
	}
	
	@Test
	public void fallbackIsWorking() {
		//Act
		var result = controller.getAllCustomers();
		
		//Assert
		assertEquals(HttpStatus.OK, result.getStatusCode());
		assertEquals(LocalFallback.BERND, result.getBody().iterator().next());
	}
	
	@Test
	public void realCallIsWorking() {
		//Arrange
	    WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/customers"))
                .willReturn(WireMock.aResponse()
                   .withBody("[{\"id\":1,\"name\":\"John Doe\",\"birthday\":\"1111-11-11\"}]")
                   .withFixedDelay(500)
                   .withHeader("Content-Type", "application/json")
                   .withStatus(HttpStatus.OK.value())));
		
		
		//Act
		var result = controller.getAllCustomers();
		
		//Assert
		assertEquals(HttpStatus.OK, result.getStatusCode());
		assertTrue(result.getBody().iterator().hasNext());
		Customer customer = result.getBody().iterator().next();
		assertEquals("John Doe", customer.getName());
		assertEquals(1L, customer.getId());
		assertEquals(LocalDate.of(1111,  11,  11), customer.getBirthday());
	}
}

