package com.example.weather.controller;

import com.example.weather.entity.City;
import com.example.weather.service.CityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/cities")
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping
    public List<City> getAllCities() {
        return cityService.getAllCities();
    }

    @GetMapping("/{id}")
    public ResponseEntity<City> getCityById(@PathVariable Long id) {
        City city = cityService.getCityById(id);
        if (city != null) {
            return ResponseEntity.ok(city);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/coordinates")
    public ResponseEntity<City> getCityByLonAndLat(@RequestParam Double lon, @RequestParam Double lat) {
        City city = cityService.getCityByLonAndLat(lon, lat);
        if (city != null) {
            return ResponseEntity.ok(city);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/weatherData")
    public List<City> getCitiesByWeatherDataDate(@RequestParam String date) {
        LocalDate parsedDate = LocalDate.parse(date);
        return cityService.getCitiesByWeatherDataDate(parsedDate);
    }

    @PostMapping
    public City createCity(@RequestBody City city) {
        return cityService.createCity(city);
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<City>> createBulkCities(@RequestBody List<City> cities) {
        List<City> createdCities = cityService.createBulkCities(cities);
        return ResponseEntity.ok(createdCities);
    }

    @PutMapping("/{id}")
    public ResponseEntity<City> updateCity(@PathVariable Long id, @RequestBody City newCityData) {
        City updatedCity = cityService.updateCity(id, newCityData);
        if (updatedCity != null) {
            return ResponseEntity.ok(updatedCity);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCity(@PathVariable Long id) {
        cityService.deleteCity(id);
        return ResponseEntity.noContent().build();
    }
}