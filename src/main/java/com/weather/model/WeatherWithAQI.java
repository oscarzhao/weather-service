package com.weather.model;

import net.aksingh.owmjapis.AbstractWeather;

public class WeatherWithAQI {
	public AbstractWeather weather;
	public AirQualityIndex air;

	public WeatherWithAQI() {
		this.weather = null;
		this.air = null;
	}

	public WeatherWithAQI(AbstractWeather weather, AirQualityIndex air) {
		this.weather = weather;
		this.air = air;
	}
}
