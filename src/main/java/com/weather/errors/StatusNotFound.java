package com.weather.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "resource not found")
public class StatusNotFound extends ErrorStatus {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public StatusNotFound() {
		super();
	}

	public StatusNotFound(String message) {
		super(message);
	}

	public StatusNotFound(Throwable cause) {
		super(cause);
	}

	public StatusNotFound(String message, Throwable cause) {
		super(message, cause);
	}

	public int getStatusCode() {
		return 404;
	}
}