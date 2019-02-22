package org.example.ws.web.api;

import java.math.BigInteger;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.example.model.Greeting;

@RestController
public class GreetingController {
	
	private static BigInteger nextId;
	private static Map<BigInteger, Greeting> greetingMap;
	
	private static Greeting save(Greeting greeting) {
		if(greetingMap == null) {
			greetingMap = new HashMap<BigInteger, Greeting>();
			nextId = BigInteger.ONE;
		}
		greeting.setId(nextId);
		nextId = nextId.add(BigInteger.ONE);
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
	
	private static boolean delete(BigInteger id) {
		Greeting deletedGreeting = greetingMap.remove(id);
		if(deletedGreeting == null) {
			return false;
		}
		return true;
	}
	
	@RequestMapping(
			value="/api/greetings",
			method=RequestMethod.GET, 
			produces= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Greeting>> getGreetings() {
		Collection<Greeting> greetings = greetingMap.values();
		return new ResponseEntity<Collection<Greeting>>(greetings, HttpStatus.OK);
	}
	
	@RequestMapping(
			value = "/api/greetings/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Greeting> getGreeting(@PathVariable("id") BigInteger id) {
		Greeting greeting = greetingMap.get(id);
		if(greeting == null) {
			return new ResponseEntity<Greeting>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Greeting>(greeting, HttpStatus.OK);
	}
	
	
	@RequestMapping(
			value = "/api/greetings/{id}",
			method = RequestMethod.PUT,
			consumer = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Greeting> updateGreeting(@ResponseBody Greeting greeting) {
		Greeting updateGreeting = save(greeting);
		if(updateGreeting == null) {
			return new ResponseEntity<Greeting>(
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<Greeting>(updatedGreeting, HttpStatus.OK);
	}
	
	
	
	@RequestMapping(value = "/api/greetings/{id}",
			method = RequestMethod.DELETE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Greeting> deleteGreeting(
			@PathVariable("id") BigInteger id, @RequestBody Greeting greeting) {
		boolean deleted = delete(id);
		if(deleted) {
			return new ResponseEntity<Greeting>(
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
