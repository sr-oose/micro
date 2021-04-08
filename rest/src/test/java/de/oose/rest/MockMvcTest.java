package de.oose.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.cloud.autoconfigure.RefreshAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
@ImportAutoConfiguration(RefreshAutoConfiguration.class)
@ActiveProfiles("test")
@Import(MockMvcTestConfig.class)
public class MockMvcTest {
	
	@Value("${grusswort}")
	private String grusswortExpected;
	
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void helloEndpointWorkingAsExpected() throws Exception {
		// Arrange
		final String nameExpected = "John Doe";
		final String uri = String.format("/hello/%s", nameExpected);
		
		// Act
		mockMvc.perform(get(uri)).andDo(print())
		// Assert
		  .andExpect(status().isOk())
		  .andExpect(content().string(String.format("%s %s", grusswortExpected, nameExpected)));
		
		assertThat(grusswortExpected).isNotBlank();
	}
}
