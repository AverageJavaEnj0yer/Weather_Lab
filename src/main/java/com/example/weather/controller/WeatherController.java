package com.example.weather.controller;

import com.example.weather.service.WeatherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.weather.service.WeatherApiResponse;
import com.example.weather.entity.City;

import com.example.weather.service.CityService;

@RestController
public class WeatherController {

    private final WeatherService weatherService;
    private final CityService cityService;

    public WeatherController(WeatherService weatherService, CityService cityService) {
        this.weatherService = weatherService;
        this.cityService = cityService;
    }

    @GetMapping("/weather")
    public ResponseEntity<Object> getWeatherByCity(@RequestParam String city) {
        WeatherApiResponse apiResponse = weatherService.getWeatherByCity(city);
        if (apiResponse != null) {
            // Сохраняем данные о городе в базу данных
            City newCity = new City(apiResponse.getName(), apiResponse.getCoord().getLon(), apiResponse.getCoord().getLat());
            cityService.createCity(newCity);
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
