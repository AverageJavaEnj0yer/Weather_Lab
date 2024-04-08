package com.example.weather.controller;

import com.example.weather.entity.City;
import com.example.weather.entity.WeatherCondition;
import com.example.weather.entity.WeatherData;
import com.example.weather.model.WeatherApiResponse;
import com.example.weather.model.WeatherConditionResponse;
import com.example.weather.service.CityService;
import com.example.weather.service.WeatherConditionService;
import com.example.weather.service.WeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
public class WeatherController {

    private final WeatherService weatherService;
    private final CityService cityService;
    private final WeatherConditionService weatherConditionService;

    private static final Logger logger = LoggerFactory.getLogger(WeatherController.class);

    public WeatherController(WeatherService weatherService, CityService cityService, WeatherConditionService weatherConditionService) {
        this.weatherService = weatherService;
        this.cityService = cityService;
        this.weatherConditionService = weatherConditionService;
    }

    @GetMapping("/weather")
    public ResponseEntity<Object> getWeatherByCity(@RequestParam String city) {
        logger.info("Fetching weather data by city: {}", city);
        WeatherApiResponse apiResponse = weatherService.getWeatherByCity(city);
        if (apiResponse != null) {
            saveWeatherData(apiResponse);
            logger.info("Weather data fetched successfully for city: {}", city);
            return ResponseEntity.ok(apiResponse);
        } else {
            logger.error("Failed to fetch weather data for city: {}", city);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/weatherByCoordinates")
    public ResponseEntity<Object> getWeatherByCoordinates(@RequestParam String lat, @RequestParam String lon) {
        logger.info("Fetching weather data by coordinates. Latitude: {}, Longitude: {}", lat, lon);
        WeatherApiResponse apiResponse = weatherService.getWeatherByCoordinates(lat, lon);
        if (apiResponse != null) {
            saveWeatherData(apiResponse);
            logger.info("Weather data fetched successfully for coordinates. Latitude: {}, Longitude: {}", lat, lon);
            return ResponseEntity.ok(apiResponse);
        } else {
            logger.error("Failed to fetch weather data for coordinates. Latitude: {}, Longitude: {}", lat, lon);
            return ResponseEntity.notFound().build();
        }
    }

    private void saveWeatherData(WeatherApiResponse apiResponse) {
        WeatherData weatherData = new WeatherData();
        weatherData.setDate(LocalDate.now());
        weatherData.setTemperature(apiResponse.getMain().getTemp());
        weatherData.setHumidity(apiResponse.getMain().getHumidity());

        City cityEntity = cityService.findByName(apiResponse.getName());
        if (cityEntity == null) {
            cityEntity = new City(apiResponse.getName(), apiResponse.getCoordinates().getLon(), apiResponse.getCoordinates().getLat());
            cityEntity = cityService.createCity(cityEntity);
        }

        // Check if weather data with the same date, temperature, humidity, and city already exists
        WeatherData existingWeatherData = weatherService.findWeatherDataByAllFields(weatherData.getDate(), weatherData.getTemperature(), weatherData.getHumidity(), cityEntity);
        if (existingWeatherData != null) {
            logger.warn("Weather data entry with identical values already exists");
            return; // No need to save if identical weather data already exists
        }

        weatherData.setCity(cityEntity);

        List<WeatherConditionResponse> weatherConditions = apiResponse.getWeather();
        List<WeatherCondition> weatherConditionEntities = new ArrayList<>();
        for (WeatherConditionResponse condition : weatherConditions) {
            WeatherCondition weatherConditionEntity = weatherConditionService.findByMainAndDescription(condition.getMain(), condition.getDescription());
            if (weatherConditionEntity == null) {
                weatherConditionEntity = new WeatherCondition(condition.getMain(), condition.getDescription(), condition.getIcon());
                weatherConditionEntity = weatherConditionService.createWeatherCondition(weatherConditionEntity);
            }
            // Check if the weather condition is already associated with the weather data
            if (!weatherData.getWeatherConditions().contains(weatherConditionEntity)) {
                weatherConditionEntities.add(weatherConditionEntity);
            }
        }

        weatherData.setWeatherConditions(weatherConditionEntities);

        weatherService.createWeatherData(weatherData);
    }
}
