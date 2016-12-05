package com.weather.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.weather.errors.ErrorStatus;
import com.weather.model.StatusResponse;

@ControllerAdvice
@RestController
public class ControllerError extends ResponseEntityExceptionHandler {
	@ExceptionHandler(Exception.class)
	@ResponseBody
	ResponseEntity<?> handleControllerException(HttpServletRequest request, Throwable ex) {
		HttpStatus status = getStatus(request);
		return new ResponseEntity<>(new StatusResponse(request.getRequestURL().toString(), ex), status);
	}

	@ExceptionHandler(ErrorStatus.class)
	@ResponseBody
	ResponseEntity<?> handleStatusNotFoundException(HttpServletRequest request, Throwable ex) {
		ErrorStatus esEx = (ErrorStatus) ex;
		return new ResponseEntity<>(new StatusResponse(request.getRequestURI(), ex),
				HttpStatus.valueOf(esEx.getStatusCode()));
	}

	private HttpStatus getStatus(HttpServletRequest request) {
		Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
		if (statusCode == null) {
			return HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return HttpStatus.valueOf(statusCode);
	}

}
