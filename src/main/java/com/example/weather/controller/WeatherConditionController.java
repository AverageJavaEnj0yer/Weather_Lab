package com.example.weather.controller;

import com.example.weather.entity.WeatherCondition;
import com.example.weather.service.WeatherConditionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/weatherConditions")
public class WeatherConditionController {


    private WeatherConditionService weatherConditionService;

    @GetMapping
    public List<WeatherCondition> getAllWeatherConditions() {
        return weatherConditionService.getAllWeatherConditions();
    }

    @GetMapping("/{id}")
    public ResponseEntity<WeatherCondition> getWeatherConditionById(@PathVariable Long id) {
        WeatherCondition weatherCondition = weatherConditionService.getWeatherConditionById(id);
        if (weatherCondition != null) {
            return ResponseEntity.ok(weatherCondition);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public WeatherCondition createWeatherCondition(@RequestBody WeatherCondition weatherCondition) {
        return weatherConditionService.createWeatherCondition(weatherCondition);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WeatherCondition> updateWeatherCondition(@PathVariable Long id, @RequestBody WeatherCondition newWeatherConditionData) {
        WeatherCondition updatedWeatherCondition = weatherConditionService.updateWeatherCondition(id, newWeatherConditionData);
        if (updatedWeatherCondition != null) {
            return ResponseEntity.ok(updatedWeatherCondition);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWeatherCondition(@PathVariable Long id) {
        weatherConditionService.deleteWeatherCondition(id);
        return ResponseEntity.noContent().build();
    }
}
