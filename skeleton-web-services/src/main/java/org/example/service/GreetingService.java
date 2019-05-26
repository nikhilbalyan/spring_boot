package org.example.service;

import java.util.Collection;

import org.example.model.Greeting;
// public business service method which we will be exposing
// to clients of the service
public interface GreetingService {
		
	Collection<Greeting> findAll();

	Greeting findOne(Long id);

	Greeting create(Greeting greeting);

	Greeting update(Greeting greeting);

	void delete(Long id);

	/**
     * Evicts all members of the "greetings" cache.
     */
    void evictCache();
}
