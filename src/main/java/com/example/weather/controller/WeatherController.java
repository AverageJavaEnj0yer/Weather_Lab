package com.example.weather.controller;

import com.example.weather.service.WeatherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.weather.service.WeatherApiResponse;
import com.example.weather.entity.City;
import com.example.weather.entity.WeatherCondition;
import com.example.weather.service.CityService;
import com.example.weather.service.WeatherConditionService;
import com.example.weather.model.WeatherConditionResponse;
import com.example.weather.repository.WeatherConditionRepository;


@RestController
public class WeatherController {

    private final WeatherService weatherService;
    private final CityService cityService;
    private final WeatherConditionService weatherConditionService;
    private final WeatherConditionRepository weatherConditionRepository;

    public WeatherController(WeatherService weatherService, CityService cityService, WeatherConditionService weatherConditionService, WeatherConditionRepository weatherConditionRepository) {
        this.weatherService = weatherService;
        this.cityService = cityService;
        this.weatherConditionService = weatherConditionService;
        this.weatherConditionRepository = weatherConditionRepository;
    }

    @GetMapping("/weather")
    public ResponseEntity<Object> getWeatherByCity(@RequestParam String city) {
        WeatherApiResponse apiResponse = weatherService.getWeatherByCity(city);
        if (apiResponse != null) {
            // Сохраняем данные о городе в базу данных
            City newCity = new City(apiResponse.getName(), apiResponse.getCoord().getLon(), apiResponse.getCoord().getLat());
            cityService.createCity(newCity);

            // Сохраняем данные о погодных условиях в базу данных
            if (apiResponse.getWeather() != null && !apiResponse.getWeather().isEmpty()) {
                for (WeatherConditionResponse condition : apiResponse.getWeather()) {
                    // Проверяем, существует ли уже такое погодное условие в базе данных
                    if (!weatherConditionRepository.existsByMainAndDescription(condition.getMain(), condition.getDescription())) {
                        // Если погодное условие не существует, сохраняем его
                        WeatherCondition newCondition = new WeatherCondition(condition.getMain(), condition.getDescription(), condition.getIcon());
                        weatherConditionService.createWeatherCondition(newCondition);
                    }
                }
            }

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
