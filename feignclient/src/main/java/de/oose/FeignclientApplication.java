package de.oose;

import java.time.Duration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import de.oose.rest.LocalFallback;
import feign.Feign;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.SlidingWindowType;
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerOnSlowCallRateExceededEvent;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.feign.FeignDecorators;
import io.github.resilience4j.feign.Resilience4jFeign;

@SpringBootApplication
@EnableFeignClients
public class FeignclientApplication {

	public static void main(String[] args) {
		SpringApplication.run(FeignclientApplication.class, args);
	}
	
    @Bean
    @Scope("prototype")
    public Feign.Builder feignBuilder() {
    	CircuitBreakerConfig config = CircuitBreakerConfig.custom()
    		.failureRateThreshold(0.01f)
    		.slidingWindowType(SlidingWindowType.COUNT_BASED)
    		.slidingWindowSize(100)
    		.minimumNumberOfCalls(10)
    		.slowCallDurationThreshold(Duration.ofSeconds(2))
    	    .slowCallRateThreshold(0.05f)
    		.build();
    	CircuitBreakerRegistry registry = CircuitBreakerRegistry.of(config);
    	//CircuitBreakerRegistry registry = CircuitBreakerRegistry.ofDefaults();
        CircuitBreaker circuitBreaker = registry.circuitBreaker("REST-SERVER");
                FeignDecorators decorators = FeignDecorators.builder()
                .withCircuitBreaker(circuitBreaker)
                .withFallback(new LocalFallback())
                .build();
        return Resilience4jFeign.builder(decorators);
    }    
}
