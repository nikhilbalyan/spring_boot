package org.example.service;

import java.util.Collection;

import org.example.model.Greeting;
import org.example.repository.GreetingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.SUPPORTS,
			   readOnly = true)
public class GreetingServiceBean implements GreetingService {
	
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    
	@Autowired
	private GreetingRepository greetingRepository;
	
	@Autowired
	private CounterService counterService;
	
//	private static Long nextId;
//	private static Map<Long, Greeting> greetingMap;
//	
//	private static Greeting save(Greeting greeting) {
//		if(greetingMap == null) {
//			greetingMap = new HashMap<Long, Greeting>();
//			nextId = (long) 1;
//		}
//		
//		// if update....
//		if(greeting.getId() != null) {
//			Greeting oldGreeting = greetingMap.get(greeting.getId());
//			if(oldGreeting == null) {
//				return null;
//			}
//			greetingMap.remove(greeting.getId());
//			greetingMap.put(greeting.getId(), greeting);
//			return greeting;
//		}
//		
//		// if create....
//		greeting.setId(nextId++);
//		nextId = (long) 1;
//		greetingMap.put(greeting.getId(), greeting);
//		return greeting;
//	}
//	
//	static {
//		Greeting g1 = new Greeting();
//		g1.setText("Hello World");
//		save(g1);
//		
//		Greeting g2 = new Greeting();
//		g2.setText("Hola Mndo!");
//		save(g2);
//	}
//
//	private static boolean remove(Long id) {
//		Greeting deletedGreeting = greetingMap.remove(id);
//		if(deletedGreeting == null) {
//			return false;
//		}
//		return true;
//	}

	@Override
	public Collection<Greeting> findAll() {
		counterService.increment("method.invoked.greetingServiceBean.findAll");;
		Collection<Greeting> greetings = greetingRepository.findAll();
		return greetings;
	}

	@Override
	@Cacheable(value = "greetings", key="#id")
	// the value element defines which element stores the cache value and 
	// key element specifies the index of cache.
	public Greeting findOne(Long id) {
		counterService.increment("method.invoked.greetingServiceBean.findOne");
		Greeting greeting = greetingRepository.findOne(id);
		return greeting;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED,
				   readOnly = false)
	public Greeting create(Greeting greeting) {
		counterService.increment("method.invoked.greetingServiceBean.create");
		if(greeting.getId() != null) {
//			cannot create Greeting with specified ID value
			return null;
		}
		Greeting savedGreeting = greetingRepository.save(greeting);
//		illustrate tx rollback
		if(savedGreeting.getId() == 4L) {
			throw new RuntimeException("Roll me back");
		}
		return savedGreeting;
	}

	@Override
	public Greeting update(Greeting greeting) {
		counterService.increment("method.invoked.greetingServiceBean.update");;
		Greeting greetingPersisted = findOne(greeting.getId());
		if (greetingPersisted == null) {
			return null;
		}
		Greeting updatedGreeting = greetingRepository.save(greeting);
		return updatedGreeting;
	}

	@Override
	public void delete(Long id) {
		counterService.increment("method.invoked.greetingServiceBean.delete");
		greetingRepository.delete(id);
	}
	
    @Override
    @CacheEvict(
            value = "greetings",
            allEntries = true)
    public void evictCache() {
        logger.info("> evictCache");
        logger.info("< evictCache");
    }

}
