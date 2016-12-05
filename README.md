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

### test

```
# mvn test
```
### Build (and Run)

```
# mvn package
# java --jar target/weather-service-xxx.jar
```

### Access the API

```
# curl -Ls -X GET http://localhost:8080/api/v1/current?city=Beijing
```

Currently, only FOUR cities (Beijing, Shanghai, Guangzhou, Shenzhen) are supported.

## Mechanism

Once the server is started, the server shall pull weather information from OpenWeatherMap every 60 minutes, and store
the response in HashMap.

When a request arrives, server shall fetch weather information from the HashMap.

When one of the upstream servers (excluding weather data server) fails to respond, code 200 is returned with additional error messages.
For example, if api server fails to fetch weather data from upstream server, or got invalid weather data, api server shall respond with 404, or 500 to client call; 
if api server fails to fetch data from upstream server(e.g. air quality), or got invalid data, api server shall respond with 200 and error messages to client call.

## TODO

### Features
1. Add air pollution status for cities (currently mock data)
2. Add weather API for more dimensions, e.g. forecast, daily

### Development
1. Find out a more graceful solution to do data communication (currently via Singleton Pattern and ConcurrentHashMap)
2. Optimize test solution
3. Introduce API management tools, for example swagger