package com.weather.controller;

import org.springframework.web.bind.annotation.RestController;

import com.weather.api.FirstTierCityAirQualityAPI;
import com.weather.api.FirstTierCityWeatherAPI;
import com.weather.errors.StatusInternalServer;
import com.weather.errors.StatusNotFound;
import com.weather.model.AirQualityIndex;
import com.weather.model.StatusResponse;
import com.weather.model.WeatherWithAQI;
import com.weather.tasks.WeatherUpdater;

import net.aksingh.owmjapis.AbstractWeather;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
@RequestMapping(value = "/api/v1/", method = RequestMethod.GET, produces = "application/json")
public class WeatherController {
	private static final Logger log = LoggerFactory.getLogger(WeatherUpdater.class);

	@RequestMapping(value = "current")
	public StatusResponse getCurrentWeather(@RequestParam(value = "city", defaultValue = "Beijing") String name) {
		FirstTierCityWeatherAPI instance = FirstTierCityWeatherAPI.getInstance();
		AbstractWeather cwd = instance.getByCityName(name);

		// ensure weather data is correct
		if (cwd == null) {
			log.info("get city {}'s current weather data fails, because no data for it", name);
			throw new StatusNotFound("city " + name + "'s current weather data does not exist");
		}
		if (cwd.isValid() == false) {
			log.info("get city {}'s current weather data fails, because upstream server returned invalid response {}",
					name, cwd.toString());
			throw new StatusInternalServer("upstream server returned invalid response");
		}

		// if air quality data is not valid, still return 200, but error
		// information recorded in messages
		FirstTierCityAirQualityAPI airInstance = FirstTierCityAirQualityAPI.getInstance();
		AirQualityIndex aqi = airInstance.getByCityName(name);
		if (aqi == null) {
			log.error("get city {}'s current air quality data fails, because no data for it", name);
		} else if (aqi.IsValid() == false) {
			log.error("get city {}'s current air quality data fails, because upstream server returns invalid data",
					name);
		}
		return new StatusResponse(new WeatherWithAQI(cwd, aqi));
	}

}