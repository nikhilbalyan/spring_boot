package org.example.ws.batch;




import java.util.Collection;

import org.example.model.Greeting;
import org.example.service.GreetingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Profile("batch")
@Component // annotating the bean class with component for ensuring the spring boot registers this class during initialization
public class GreetingBatchBean {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private GreetingService greetingService;
	
	// scheduler that runs at 0 and 30 secs of every min of every hour of every day
//	@Scheduled(cron="0.30 * * * * *")
//	@Scheduled(fixedDelay = 1000)
	public  void cronJob() {
		logger.info("> cronJob");
		
		// add scheduled logic here
		Collection<Greeting> greetings = greetingService.findAll();
		logger.info("There are {} greetings in the data store",
				greetings.size());
		
		logger.info("< cronJob");
	}
	
	@Scheduled(
			initialDelay = 5000,
			fixedRate = 15000)
	public void fixedRateJobWithInitialDelay() {
		logger.info("> fixedRateJobWithInitialDelay");
		
		// Add scheduled logic here
		// Simulate Job processing time
		
		long pause = 5000;
		long start = System.currentTimeMillis();
		do {
			if ( start + pause < System.currentTimeMillis()) { // it will add 5 seconds pause before
															   // the execution of program further
				break;
			}
		} while (true);
		logger.info("processing time was {} seconds", pause/1000);
		logger.info("< fixedRateJobWithInitialDelay");
	}
	
	
	@Scheduled(
			initialDelay = 5000,
			fixedDelay = 15000)
	public void fixedDelayJobWithInitialDelay() {
		logger.info("> fixedDelayJobWithInitialDelay");
		
		
		// Add scheduled logic here
		// simulate job processing time
		long pause = 5000;
		long start = System.currentTimeMillis();
		do {
			if (start + pause < System.currentTimeMillis()) {
				break;
			}
		} while (true);
		logger.info("processing time was {} seconds.", pause / 1000);
		logger.info("< fixedDelayJobWithInitialDelay");
	}
}