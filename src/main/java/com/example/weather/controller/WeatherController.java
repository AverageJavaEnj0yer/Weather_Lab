package com.example.weather.controller;

import com.example.weather.entity.City;
import com.example.weather.entity.WeatherCondition;
import com.example.weather.entity.WeatherData;
import com.example.weather.model.WeatherConditionResponse;
import com.example.weather.service.CityService;
import com.example.weather.service.WeatherConditionService;
import com.example.weather.service.WeatherService;
import com.example.weather.model.WeatherApiResponse;
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

    public WeatherController(WeatherService weatherService, CityService cityService, WeatherConditionService weatherConditionService) {
        this.weatherService = weatherService;
        this.cityService = cityService;
        this.weatherConditionService = weatherConditionService;
    }

    @GetMapping("/weather")
    public ResponseEntity<Object> getWeatherByCity(@RequestParam String city) {
        WeatherApiResponse apiResponse = weatherService.getWeatherByCity(city);
        if (apiResponse != null) {
            double temperature = apiResponse.getMain().getTemp();
            double humidity = apiResponse.getMain().getHumidity();

            WeatherData weatherData = new WeatherData();
            weatherData.setDate(LocalDate.now());
            weatherData.setTemperature(temperature);
            weatherData.setHumidity(humidity);

            City cityEntity = cityService.findByName(city);
            if (cityEntity == null) {
                cityEntity = new City(city, apiResponse.getCoord().getLon(), apiResponse.getCoord().getLat());
                cityEntity = cityService.createCity(cityEntity);
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
                weatherConditionEntities.add(weatherConditionEntity);
            }

            weatherData.setWeatherConditions(weatherConditionEntities);

            weatherService.createWeatherData(weatherData);

            return ResponseEntity.ok(apiResponse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/weatherByCoordinates")
    public ResponseEntity<Object> getWeatherByCoordinates(@RequestParam String lat, @RequestParam String lon) {
        Object apiResponse = weatherService.getWeatherByCoordinates(lat, lon);
        return ResponseEntity.ok(apiResponse);
    }
}

