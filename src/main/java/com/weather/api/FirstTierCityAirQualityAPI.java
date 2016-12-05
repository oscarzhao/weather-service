package com.weather.api;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.weather.model.AirQualityIndex;
import com.weather.model.City;

public class FirstTierCityAirQualityAPI {
	private final City[] cities = new City[] { new City(1816670L, "Beijing", "100000", "CN", 39.907501, 116.397232),
			new City(1796236L, "Shanghai", "200000", "CN", 31.222219, 121.458061),
			new City(1795565L, "Shenzhen", "518000", "CN", 22.545540, 114.068298),
			new City(1809858L, "Guangzhou", "510000", "CN", 23.116671, 113.250000) };

	private final ConcurrentMap<String, AirQualityIndex> airQualityData = new ConcurrentHashMap<String, AirQualityIndex>(
			4);

	private FirstTierCityAirQualityAPI() {
	}

	public City[] listCities() {
		return cities;
	}

	public Iterable<AirQualityIndex> listAll() {
		return this.airQualityData.values();
	}

	public AirQualityIndex save(String cityName, AirQualityIndex data) {
		this.airQualityData.put(cityName, data);
		return data;
	}

	public AirQualityIndex getByCityName(String cityName) {
		return this.airQualityData.get(cityName);
	}

	public void deleteByCityName(String cityName) {
		this.airQualityData.remove(cityName);
	}

	// nested class shall be initialized at the first creation of nesting class
	private static class SingletonHelper {
		private static final FirstTierCityAirQualityAPI INSTANCE = new FirstTierCityAirQualityAPI();
	}

	public static FirstTierCityAirQualityAPI getInstance() {
		return SingletonHelper.INSTANCE;
	}
}
