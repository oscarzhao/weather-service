package com.weather.errors;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public abstract class ErrorStatus extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ErrorStatus() {
		super();
	}
	
	
	public ErrorStatus(String message) {
		super(message);
	}
	
    public ErrorStatus(Throwable cause) {
        super(cause);
    }
    
	
    public ErrorStatus(String message, Throwable cause) {
        super(message, cause);
    }
    
    // getStatusCode is to be implemented by subclasses, for customization of response code
    public abstract int getStatusCode();
}
