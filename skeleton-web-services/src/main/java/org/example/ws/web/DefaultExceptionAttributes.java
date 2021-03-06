package org.example.ws.web;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;

public class DefaultExceptionAttributes implements ExceptionAttributes {

	public static final String TIMESTAMP = "timestamp";
	public static final String STATUS = "status";
	public static final String ERROR = "error";
	public static final String EXCEPTION = "exception";
	public static final String MESSAGE = "message";
	public static final String PATH = "path";
	
	
	@Override
	public Map<String, Object> getExceptionAttributes(Exception exception, HttpServletRequest httpRequest,
			HttpStatus httpStatus) {
		// TODO Auto-generated method stub
		  Map<String, Object> exceptionAttributes = new LinkedHashMap<String, Object>();

	        exceptionAttributes.put(TIMESTAMP, new Date());
	        addHttpStatus(exceptionAttributes, httpStatus);
	        addExceptionDetail(exceptionAttributes, exception);
	        addPath(exceptionAttributes, httpRequest);

	        return exceptionAttributes;
	}
	
	 /**
     * Adds the status and error attribute values from the {@link HttpStatus}
     * value.
     * @param exceptionAttributes The Map of exception attributes.
     * @param httpStatus The HttpStatus enum value.
     */
    private void addHttpStatus(Map<String, Object> exceptionAttributes,
            HttpStatus httpStatus) {
        exceptionAttributes.put(STATUS, httpStatus.value());
        exceptionAttributes.put(ERROR, httpStatus.getReasonPhrase());
    }

    /**
     * Adds the exception and message attribute values from the
     * {@link Exception}.
     * @param exceptionAttributes The Map of exception attributes.
     * @param exception The Exception object.
     */
    private void addExceptionDetail(Map<String, Object> exceptionAttributes,
            Exception exception) {
        exceptionAttributes.put(EXCEPTION, exception.getClass().getName());
        exceptionAttributes.put(MESSAGE, exception.getMessage());
    }

    /**
     * Adds the path attribute value from the {@link HttpServletRequest}.
     * @param exceptionAttributes The Map of exception attributes.
     * @param httpRequest The HttpServletRequest object.
     */
    private void addPath(Map<String, Object> exceptionAttributes,
            HttpServletRequest httpRequest) {
        exceptionAttributes.put(PATH, httpRequest.getServletPath());
    } 

}
