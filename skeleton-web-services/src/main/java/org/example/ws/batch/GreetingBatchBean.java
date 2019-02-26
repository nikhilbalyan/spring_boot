package org.example.ws.batch;




import java.util.Collection;

import org.example.model.Greeting;
import org.example.service.GreetingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component // annotating the bean class with component for ensuring the spring boot registers this class during initialization
public class GreetingBatchBean {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private GreetingService greetingService;
	
	// scheduler that runs at 0 and 30 secs of every min of every hour of every day
	@Scheduled(cron = "0.30 * * * * *")
	public  void cronJob() {
		logger.info("> cronJob");
		
		// add scheduled logic here
		Collection<Greeting> greetings = greetingService.findAll();
		logger.info("There are {} greetings in the data store",
				greetings.size());
		
		logger.info("< cronJob");
	}
	
}
