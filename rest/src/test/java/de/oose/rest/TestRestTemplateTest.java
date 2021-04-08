package de.oose.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import de.oose.domain.Customer;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class TestRestTemplateTest {
	
	@LocalServerPort
	private int port;

	@Value("${grusswort}")
	private String grusswortExpected;
	
	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void helloEndpointWorkingAsExpected() {
		// Arrange
		final String nameExpected = "John Doe";
		final String uri = String.format("/hello/%s", nameExpected);
		
		// Act
		var result = restTemplate.getForEntity(uri, String.class);

		// Assert
		assertThat(grusswortExpected).isNotBlank();
		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(result.getBody()).isEqualTo(String.format("%s %s", grusswortExpected, nameExpected));
	}
}
