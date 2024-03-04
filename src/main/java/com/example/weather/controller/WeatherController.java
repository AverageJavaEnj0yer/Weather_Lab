package com.example.weather.controller;

import com.example.weather.service.WeatherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/weather")
    public ResponseEntity<Object> getWeatherByCity(@RequestParam String city) {
        Object apiResponse = weatherService.getWeatherByCity(city);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/weatherByCoordinates")
    public ResponseEntity<Object> getWeatherByCoordinates(@RequestParam String lat, @RequestParam String lon) {
        Object apiResponse = weatherService.getWeatherByCoordinates(lat, lon);
        return ResponseEntity.ok(apiResponse);
    }
}