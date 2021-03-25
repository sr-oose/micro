package de.oose.rest;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import reactor.core.publisher.Flux;

@TestConfiguration
public class LoadBalancerConfig {

	@Bean
	@Primary
	ServiceInstanceListSupplier serviceInstanceListSupplier() {
		return new ServiceInstanceListSupplier() {

			@Override
			public Flux<List<ServiceInstance>> get() {
				return Flux.just(Arrays
						.asList(new DefaultServiceInstance("REST-SERVER1", "REST-SERVER", "localhost", 8080, false)));
			}

			@Override
			public String getServiceId() {
				return "REST-SERVER";
			}

		};
	}

}
