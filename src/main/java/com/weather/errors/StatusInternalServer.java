package com.weather.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "internal server error") // 500
public class StatusInternalServer extends ErrorStatus {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public StatusInternalServer() {
		super();
	}

	public StatusInternalServer(String message) {
		super(message);
	}

	public StatusInternalServer(Throwable cause) {
		super(cause);
	}

	public StatusInternalServer(String message, Throwable cause) {
		super(message, cause);
	}

	public int getStatusCode() {
		return 500;
	}
}
