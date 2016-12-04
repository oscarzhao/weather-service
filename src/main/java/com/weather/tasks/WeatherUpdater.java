package com.weather.tasks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.json.JSONException;

import net.aksingh.owmjapis.CurrentWeather;
import net.aksingh.owmjapis.OpenWeatherMap;


import com.weather.api.FirstTierCityWeatherAPI;
import com.weather.model.City;

@Component
public class WeatherUpdater {
    private static final Logger log = LoggerFactory.getLogger(WeatherUpdater.class);

    private static OpenWeatherMap owm;
    
    public WeatherUpdater() {
    	owm = new OpenWeatherMap("");
    	owm.setApiKey("34f13a1b88e200d2ac2354065b816d25");
    }
   
    @Scheduled(fixedRate = 3600000)
    public void updateFirstTierCityWeather() { // run one time per hour
    	FirstTierCityWeatherAPI instance = FirstTierCityWeatherAPI.getInstance();
    	City[] cities = instance.listCities();
    	
    	if (cities.length == 0) return;
    	
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

}
