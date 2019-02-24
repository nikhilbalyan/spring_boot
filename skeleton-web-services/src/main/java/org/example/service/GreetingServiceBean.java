package org.example.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.example.model.Greeting;
import org.springframework.stereotype.Service;

@Service
public class GreetingServiceBean implements GreetingService {
	
	
	
	private static Long nextId;
	private static Map<Long, Greeting> greetingMap;
	
	private static Greeting save(Greeting greeting) {
		if(greetingMap == null) {
			greetingMap = new HashMap<Long, Greeting>();
			nextId = (long) 1;
		}
		
		// if update....
		if(greeting.getId() != null) {
			Greeting oldGreeting = greetingMap.get(greeting.getId());
			if(oldGreeting == null) {
				return null;
			}
			greetingMap.remove(greeting.getId());
			greetingMap.put(greeting.getId(), greeting);
			return greeting;
		}
		
		// if create....
		greeting.setId(nextId++);
		nextId = (long) 1;
		greetingMap.put(greeting.getId(), greeting);
		return greeting;
	}
	
	static {
		Greeting g1 = new Greeting();
		g1.setText("Hello World");
		save(g1);
		
		Greeting g2 = new Greeting();
		g2.setText("Hola Mndo!");
		save(g2);
	}

	private static boolean remove(Long id) {
		Greeting deletedGreeting = greetingMap.remove(id);
		if(deletedGreeting == null) {
			return false;
		}
		return true;
	}

	@Override
	public Collection<Greeting> findAll() {
		Collection<Greeting> greetings = greetingMap.values();
		return greetings;
	}

	@Override
	public Greeting findOne(Long id) {
		Greeting greeting = greetingMap.get(id);
		return greeting;
	}

	@Override
	public Greeting create(Greeting greeting) {
		Greeting savedGreeting = save(greeting);
		return savedGreeting;
	}

	@Override
	public Greeting update(Greeting greeting) {
		Greeting updatedGreeting = save(greeting);
		return updatedGreeting;
	}

	@Override
	public void delete(Long id) {
		remove(id);
	}

}
