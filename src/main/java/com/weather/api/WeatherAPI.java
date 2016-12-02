package com.weather.api;

import net.aksingh.owmjapis.CurrentWeather;

public interface WeatherAPI {
	public Iterable<CurrentWeather> listAll() ;

	public CurrentWeather save(String cityName, CurrentWeather data);

	public CurrentWeather getByCityName(String cityName) ;

	public void deleteByCityName(String cityName);
	
}
