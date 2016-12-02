package com.weather.tasks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.json.JSONException;

import java.net.MalformedURLException;
import net.aksingh.owmjapis.CurrentWeather;
import net.aksingh.owmjapis.OpenWeatherMap;


import com.weather.api.FirstTierCityWeatherAPI;
import com.weather.city.City;

@Component
public class WeatherUpdater {
    private static final Logger log = LoggerFactory.getLogger(WeatherUpdater.class);

    private static OpenWeatherMap owm;
    
    public WeatherUpdater() {
    	owm = new OpenWeatherMap("");
    	owm.setApiKey("34f13a1b88e200d2ac2354065b816d25");
    }
   
    @Scheduled(fixedRate = 600000)
    public void updateFirstTierCityWeather() { // unit is millisecond
    	FirstTierCityWeatherAPI instance = FirstTierCityWeatherAPI.getInstance();
    	City[] cities = instance.listCities();
    	if (cities.length == 0) return;
    	
    	// http://api.openweathermap.org/data/2.5/group?id=524901,703448,2643743&units=metric&appid=34f13a1b88e200d2ac2354065b816d25
		try {
			for (int idx = 0; idx < cities.length; idx++) {
		        // getting current weather data for the idx city
		        CurrentWeather cwd = owm.currentWeatherByCityCode(cities[idx].getID());
		        log.debug("weather info.  base station: {}", cwd.getBaseStation());
		        log.debug("city code: {}, name: {}", cwd.getCityCode(), cwd.getCityName());
		        log.debug("humidity: {}", cwd.getMainInstance().getHumidity());
		        log.debug("pressure: {}", cwd.getMainInstance().getPressure());
		        log.debug("temperature min:{}, max:{}, normal:{}", cwd.getMainInstance().getMinTemperature(), cwd.getMainInstance().getMaxTemperature(), cwd.getMainInstance().getTemperature());
		        
		        log.debug("cloud:     {}", cwd.getCloudsInstance().getPercentageOfClouds());
		        log.debug("wind degree: {}, speed:{}", cwd.getWindInstance().getWindDegree(), cwd.getWindInstance().getWindSpeed());
		        log.debug("snow:     {}", cwd.getRainInstance());
		        
		        instance.save(cwd.getCityName(), cwd);
			}
		} catch (JSONException e) {
			log.error("parse json fails, error:");
			e.printStackTrace();
		}
    }

}
