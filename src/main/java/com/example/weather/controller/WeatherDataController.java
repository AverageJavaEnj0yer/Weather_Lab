package com.example.weather.controller;

import com.example.weather.entity.WeatherData;
import com.example.weather.service.WeatherDataService;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/weatherData")
public class WeatherDataController {

    private final WeatherDataService weatherDataService;

    public WeatherDataController(WeatherDataService weatherDataService) {
        this.weatherDataService = weatherDataService;
    }

    @GetMapping
    public List<WeatherData> getAllWeatherData() {
        return weatherDataService.getAllWeatherData();
    }

    @GetMapping("/{id}")
    public ResponseEntity<WeatherData> getWeatherDataById(@PathVariable Long id) {
        WeatherData weatherData = weatherDataService.getWeatherDataById(id);
        if (weatherData != null) {
            return ResponseEntity.ok(weatherData);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @Hidden
    @PostMapping
    public WeatherData createWeatherData(@RequestBody WeatherData weatherData) {
        return weatherDataService.createWeatherData(weatherData);
    }
    @Hidden
    @PutMapping("/{id}")
    public ResponseEntity<WeatherData> updateWeatherData(@PathVariable Long id, @RequestBody WeatherData newWeatherData) {
        WeatherData updatedWeatherData = weatherDataService.updateWeatherData(id, newWeatherData);
        if (updatedWeatherData != null) {
            return ResponseEntity.ok(updatedWeatherData);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @Hidden
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWeatherData(@PathVariable Long id) {
        weatherDataService.deleteWeatherData(id);
        return ResponseEntity.noContent().build();
    }
}
