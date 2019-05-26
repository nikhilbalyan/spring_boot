package org.example.ws;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebAppConfiguration
public abstract class AbstractControllerTest extends AbstractTest {

//	@Autowired
	protected MockMvc mvc;
	
	@Autowired
	protected WebApplicationContext webApplicationContext;
	
	// here setup method is preparing the spring mock framework
	// for the execution of UT
	protected void setUp() {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	// here i am setting up the controller using standalone method of 
	// spring mock framework which sets up only the Controller supplied
	// and its dependencies instead of setting up the whole webapplication
	// this approach allows the controller to register the controller
	// with mockito mock dependencies.
	protected void setUp(GreetingController controller) {
		mvc = MockMvcBuilders.standaloneSetup(controller).build();
	}
	
	// when we are using mockito we most likely do not want to initialize
	// the whole application and in those cases 
	
	// jackson mapper mapToJson for mapping java and json
	protected String mapToJson(Object obj) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(obj);
	}
	
	// jackson mapper mapFromJson for mapping java and json
	protected <T> T mapFromJson(String json, Class<T> clazz) 
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(json, clazz);
	}
	
	
}
