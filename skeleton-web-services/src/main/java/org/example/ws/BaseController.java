package org.example.ws;

import java.util.Map;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;

import org.example.ws.web.DefaultExceptionAttributes;
import org.example.ws.web.ExceptionAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class BaseController {

	/**
	 * The Logger for this class hierarchy 
	 * 
	 * We have named the logger scope protected so that it is visible
	 * inside the class implementing the BaseController  
	 */
	protected Logger logger = LoggerFactory.getLogger(this.getClass()); 
	
	/**
	 * This method is for handling the exception here we have specified the Exception.class
	 * but we can specify as many classes as we want and the purpose of ExceptionHandler annotation
	 * is to help the STS with the identification of the method which can handle the error and which
	 * types of error that can handle
	 * 
	 * @param exception
	 * @param request
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, Object>> handleException(Exception exception,
			HttpServletRequest request) {
		logger.error("> handleException");
		logger.error("- Exception:", exception);
		
		ExceptionAttributes exceptionAttributes = new DefaultExceptionAttributes();
		Map<String, Object> responseBody = exceptionAttributes.getExceptionAttributes(exception, request,
				HttpStatus.INTERNAL_SERVER_ERROR);
		
		logger.error("< handleException");
		
		return new ResponseEntity<Map<String, Object>>(responseBody,
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	/**
     * Handles JPA NoResultExceptions thrown from web service controller
     * methods. Creates a response with Exception Attributes as JSON and HTTP
     * status code 404, not found.
     * 
     * @param noResultException A NoResultException instance.
     * @param request The HttpServletRequest in which the NoResultException was
     *        raised.
     * @return A ResponseEntity containing the Exception Attributes in the body
     *         and HTTP status code 404.
     */
    @ExceptionHandler(NoResultException.class)
    public ResponseEntity<Map<String, Object>> handleNoResultException(
            NoResultException noResultException, HttpServletRequest request) {

        logger.info("> handleNoResultException");

        ExceptionAttributes exceptionAttributes = new DefaultExceptionAttributes();

        Map<String, Object> responseBody = exceptionAttributes
                .getExceptionAttributes(noResultException, request,
                        HttpStatus.NOT_FOUND);

        logger.info("< handleNoResultException");
        return new ResponseEntity<Map<String, Object>>(responseBody,
                HttpStatus.NOT_FOUND);
    }

	
}
