package com.weather.tasks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.json.JSONException;

import net.aksingh.owmjapis.CurrentWeather;
import net.aksingh.owmjapis.OpenWeatherMap;

import com.weather.api.FirstTierCityAirQualityAPI;
import com.weather.api.FirstTierCityWeatherAPI;
import com.weather.model.AirQualityIndex;
import com.weather.model.City;

@Component
public class WeatherUpdater {
	private static final Logger log = LoggerFactory.getLogger(WeatherUpdater.class);

	private static OpenWeatherMap owm;

	@Value("${api.openweathermap.token}")
	private String apiToken;

	public WeatherUpdater() {
		owm = new OpenWeatherMap("");
	}

	@Scheduled(fixedRate = 3600000)
	public void updateFirstTierCityWeather() { // run one time per hour
		FirstTierCityWeatherAPI instance = FirstTierCityWeatherAPI.getInstance();
		City[] cities = instance.listCities();

		if (cities.length == 0)
			return;

		// set api token here, because set it in constructor fails
		owm.setApiKey(this.apiToken);

		try {
			for (int idx = 0; idx < cities.length; idx++) {
				// getting current weather data for the idx city
				CurrentWeather cwd = owm.currentWeatherByCityCode(cities[idx].getID());
				log.debug("weather info.  base station: {}", cwd.toString());

				instance.save(cwd.getCityName(), cwd);
			}
		} catch (JSONException e) {
			log.error("parse json fails, error:");
			e.printStackTrace();
		}
	}

	@Scheduled(fixedRate = 3600000)
	public void updateFirstTierCityAirQualityIndex() { // run one time per hour
		FirstTierCityAirQualityAPI instance = FirstTierCityAirQualityAPI.getInstance();
		City[] cities = instance.listCities();

		if (cities.length == 0)
			return;

		// set api token here, because set it in constructor fails
		owm.setApiKey(this.apiToken);

		try {
			for (int idx = 0; idx < cities.length; idx++) {
				// getting current AQI data for the idx city
				// here data is mocked
				AirQualityIndex aqi = new AirQualityIndex(38, 14, 27, 7, 2, 3);

				instance.save(cities[idx].getName(), aqi);
			}
		} catch (JSONException e) {
			log.error("parse json fails, error:");
			e.printStackTrace();
		}
	}
}
