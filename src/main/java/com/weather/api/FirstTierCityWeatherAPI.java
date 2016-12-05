package com.weather.api;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.weather.model.City;

import net.aksingh.owmjapis.CurrentWeather;

/*
 * city list retrieved from http://openweathermap.org/help/city_list.txt
 */

public class FirstTierCityWeatherAPI implements WeatherAPI {
	private final City[] cities = new City[] { 
			new City(1816670L, "Beijing", "100000", "CN", 39.907501, 116.397232),
			new City(1796236L, "Shanghai", "200000", "CN", 31.222219, 121.458061),
			new City(1795565L, "Shenzhen", "518000", "CN", 22.545540, 114.068298),
			new City(1809858L, "Guangzhou", "510000", "CN", 23.116671, 113.250000) };

	private final ConcurrentMap<String, CurrentWeather> weatherData = new ConcurrentHashMap<String, CurrentWeather>(4);

	private FirstTierCityWeatherAPI() {
	}

	public City[] listCities() {
		return cities;
	}

	@Override
	public Iterable<CurrentWeather> listAll() {
		return this.weatherData.values();
	}

	@Override
	public CurrentWeather save(String cityName, CurrentWeather data) {
		this.weatherData.put(cityName, data);
		return data;
	}

	@Override
	public CurrentWeather getByCityName(String cityName) {
		return this.weatherData.get(cityName);
	}

	@Override
	public void deleteByCityName(String cityName) {
		this.weatherData.remove(cityName);
	}

	// nested class shall be initialized at the first creation of nesting class
	private static class SingletonHelper {
		private static final FirstTierCityWeatherAPI INSTANCE = new FirstTierCityWeatherAPI();
	}

	public static FirstTierCityWeatherAPI getInstance() {
		return SingletonHelper.INSTANCE;
	}

}
