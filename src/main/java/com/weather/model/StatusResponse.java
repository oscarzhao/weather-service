package com.weather.model;

public class StatusResponse {
	public final String path;
	public final String reason;
	public final StatusErrorMessage[] messages;
	public final WeatherWithAQI data;

	public StatusResponse(String path, Throwable ex) {
		this.path = path;
		this.reason = ex.getMessage();
		this.data = null;
		this.messages = null;
	}

	public StatusResponse(String path, String reason) {
		this.path = path;
		this.reason = reason;
		this.messages = null;
		this.data = null;
	}

	public StatusResponse(WeatherWithAQI data) {
		this.path = null;
		this.reason = null;
		this.data = data;

		if (data.air == null) {
			this.messages = new StatusErrorMessage[1];
			this.messages[0].field = "air";
			this.messages[1].message = "no data returned from upstream server";
		} else if (data.air.IsValid() == false) {
			this.messages = new StatusErrorMessage[1];
			this.messages[0].field = "air";
			this.messages[1].message = "invalid reponse from upstream server";
		} else {
			this.messages = null;
		}
	}

}
