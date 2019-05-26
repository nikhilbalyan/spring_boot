package org.example.ws.actuator.health;

import java.util.Collection;

import org.example.model.Greeting;
import org.example.service.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

//this class is annotated as a bean so that the spring will
// automatically register this class as a bean the moment it will come
// across the annotation @Component.
@Component  
public class GreetingHealthIndicator implements HealthIndicator {

	@Autowired
	private GreetingService greetingService;
	
	@Override  // returns the Health object resulting from health indicator checks
	public Health health() {
		Collection<Greeting> greetings = greetingService.findAll();
		if(greetings == null || greetings.size() == 0 ) {
			return Health.down().withDetail("count", 0).build(); // here we are adding custom
										// attributes to the Health object by calling the withDetail
										// method
		}
		return Health.up().withDetail("count", greetings.size()).build();
	}

}
