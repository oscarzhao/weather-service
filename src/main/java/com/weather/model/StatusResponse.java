package com.weather.model;

import net.aksingh.owmjapis.AbstractWeather;

public class StatusResponse {
	public final String path;
	public final String message;
	public final AbstractWeather data;
	
	public StatusResponse(String path, Throwable ex){
		this.path = path;
		this.message = ex.getMessage();
		this.data = null;
	}
	
	public StatusResponse(String path, String message){
		this.path = path;
		this.message = message;
		this.data = null;
	}
	
	public StatusResponse(AbstractWeather data) {
		this.path = null;
		this.message = null;
		this.data = data;
	}
}
