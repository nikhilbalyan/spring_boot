package org.example.service;

import java.util.Collection;

import org.example.model.Greeting;
import org.springframework.stereotype.Service;

@Service
public class GreetingServiceBean implements GreetingService {

	@Override
	public Collection<Greeting> findAll() {
		Collection<Greeting> greetings = greetingMap.values();
		return greetings;
	}

	@Override
	public Greeting findOne(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Greeting create(Greeting greeting) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Greeting update(Greeting greeting) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

}
