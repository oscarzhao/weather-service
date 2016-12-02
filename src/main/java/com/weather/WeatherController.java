package com.weather;

import org.springframework.web.bind.annotation.RestController;

import com.weather.api.FirstTierCityWeatherAPI;

import net.aksingh.owmjapis.CurrentWeather;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;


@RestController
@RequestMapping(value="/api/", method=RequestMethod.GET)
public class WeatherController {
  
  @RequestMapping(value="weather/current")
  public CurrentWeather greeting(@RequestParam(value="city", defaultValue="Beijing") String name){
	  FirstTierCityWeatherAPI instance = FirstTierCityWeatherAPI.getInstance();
	  CurrentWeather cwd = instance.getByCityName(name);
	  return cwd;
  }
}