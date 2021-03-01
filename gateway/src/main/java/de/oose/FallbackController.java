package de.oose;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallbackController {
	
	@GetMapping("/fallback")
	public ResponseEntity<String> fallback(){
		return ResponseEntity.ok("Fallback");
	}
}
