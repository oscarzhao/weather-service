# Weather Service

Weather Service provides weather and air condition data of first-tier cities in China (Beijing, Shanghai, 
Guangzhou, Shenzhen) in json format.  Weather data is provided by [OpenWeatherMap](https://openweathermap.org/ "OpenWeatherMap").

The project is based on Spring Boot, exposing APIs for GET weather and air condition information.

## Getting Started

### Requirements

1. Java 1.8+
2. maven 3.2+

### Run

```
# git clone https://github.com/oscarzhao/weather-service
# cd weather-service
# mvn spring-boot:run
```

### Build (and Run)

```
# mvn package
# java --jar target/weather-service-xxx.jar
```

### Access the API

```
# curl -Ls -X GET http://localhost:8080/api/weather/current?city=Beijing
```

Currently, only FOUR cities (Beijing, Shanghai, Guangzhou, Shenzhen) are supported.

## Mechanism

Once the server is started, the server shall pull weather information from OpenWeatherMap every 10 minutes, and store
the response in HashMap.

When a request arrives, server shall fetch weather information from the HashMap.

## TODO

1. Add air pollution status for cities (merge with weather data, currently paid OpenWeatherMap API is necessary)
3. More graceful solution to update data (currently via Singleton Pattern and ConcurrentHashMap)
4. Add tests