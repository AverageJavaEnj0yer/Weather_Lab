package com.example.weather.service;

import com.example.weather.entity.City;
import com.example.weather.exception.CityAlreadyExistsException;
import com.example.weather.cache.WeatherDataCache;
import com.example.weather.repository.CityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class CityService {
    private static final Logger logger = LoggerFactory.getLogger(CityService.class);
    private final CityRepository cityRepository;
    private final WeatherDataCache weatherDataCache;
    private final RequestCounterService requestCounterService;

    @Autowired
    public CityService(CityRepository cityRepository, WeatherDataCache weatherDataCache, RequestCounterService requestCounterService) {
        this.cityRepository = cityRepository;
        this.weatherDataCache = weatherDataCache;
        this.requestCounterService = requestCounterService;
    }

    public City findByName(String name) {
        return cityRepository.findByName(name);
    }

    public List<City> getAllCities() {
        logger.info("Fetching all cities.");
        return cityRepository.findAll();
    }

    public City getCityById(Long id) {
        logger.info("Fetching city by ID: {}", id);
        return cityRepository.findById(id).orElse(null);
    }

    public City getCityByLonAndLat(Double lon, Double lat) {
        logger.info("Fetching city by longitude: {}, latitude: {}", lon, lat);
        return cityRepository.findByLonAndLat(lon, lat);
    }

    public City createCity(City city) {
        logger.info("Creating city");
        if (cityRepository.existsByName(city.getName())) {
            throw new CityAlreadyExistsException("City with this name already exists.");
        }
        return cityRepository.save(city);
    }
    public List<City> createBulkCities(List<City> cities) {
        List<City> createdCities = new ArrayList<>();
        for (City city : cities) {
            if (!cityRepository.existsByName(city.getName())) {
                createdCities.add(cityRepository.save(city));
            }
        }
        return createdCities;
    }

    public City updateCity(Long id, City newCityData) {
        logger.info("Updating city with ID: {}", id);
        City cityToUpdate = cityRepository.findById(id).orElse(null);
        if (cityToUpdate != null) {
            if (!cityToUpdate.getName().equals(newCityData.getName())) {
                weatherDataCache.clearCache();
                logger.info("Weather data cache cleared due to city update");
            }
            if (cityRepository.existsByNameAndIdNot(newCityData.getName(), id)) {
                throw new CityAlreadyExistsException("City with this name already exists.");
            }
            cityToUpdate.setName(newCityData.getName());
            cityToUpdate.setLon(newCityData.getLon());
            cityToUpdate.setLat(newCityData.getLat());
            return cityRepository.save(cityToUpdate);
        }
        return null;
    }

    public void deleteCity(Long id) {
        logger.info("Deleting city with ID: {}", id);
        cityRepository.deleteById(id);
        weatherDataCache.clearCache();
        logger.info("Weather data cache cleared due to city deletion");
    }

    public List<City> getCitiesByWeatherDataDate(LocalDate date) {
        logger.info("Fetching cities by weather data date: {}", date);
        List<City> cachedCities = weatherDataCache.getCitiesFromCache(date);
        if (!cachedCities.isEmpty()) {
            logger.info("Cache was used for date: {}", date);
            requestCounterService.incrementCounterByDate(date); // Увеличиваем счетчик обращений по дате
            return cachedCities;
        }
        List<City> cities = cityRepository.findCitiesByWeatherDataDate(date);
        weatherDataCache.addToCache(date, cities);
        requestCounterService.incrementCounterByDate(date); // Увеличиваем счетчик обращений по дате
        return cities;
    }

}
