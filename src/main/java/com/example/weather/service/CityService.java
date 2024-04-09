package com.example.weather.service;

import com.example.weather.cache.WeatherDataCache;
import com.example.weather.entity.City;
import com.example.weather.exception.CityAlreadyExistsException;
import com.example.weather.exception.LogException;
import com.example.weather.repository.CityRepository;
import com.example.weather.repository.WeatherDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.List;

@Service
public class CityService {
    private static final Logger logger = LoggerFactory.getLogger(CityService.class);
    private final CityRepository cityRepository;

    private final WeatherDataCache weatherDataCache;
    private final RequestCounterService requestCounterService;
    private final WeatherDataRepository weatherDataRepository;

    @Autowired
    public CityService(CityRepository cityRepository, WeatherDataCache weatherDataCache, RequestCounterService requestCounterService, WeatherDataRepository weatherDataRepository) {
        this.cityRepository = cityRepository;
        this.weatherDataCache = weatherDataCache;
        this.requestCounterService = requestCounterService;
        this.weatherDataRepository = weatherDataRepository;
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

    @LogException
    public City createCity(City city) {
        logger.info("Creating city");
        if (cityRepository.existsByName(city.getName())) {
            throw new CityAlreadyExistsException("City with this name already exists.");
        }
        return cityRepository.save(city);
    }

    public List<City> createBulkCities(List<City> cities) {
        List<City> existingCities = cities.stream()
                .filter(city -> cityRepository.existsByName(city.getName()))
                .toList(); // Заменяем collect(Collectors.toList()) на toList()

        List<City> createdCities = cities.stream()
                .filter(city -> !cityRepository.existsByName(city.getName()))
                .map(cityRepository::save)
                .toList(); // Заменяем collect(Collectors.toList()) на toList()

        existingCities.forEach(city -> logger.warn("City with name '{}' already exists", city.getName()));
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
        weatherDataRepository.deleteByCityId(id); // Удаляем все записи WeatherData, связанные с этим городом
        weatherDataCache.clearCache(); // Очищаем кеш погодных данных
        logger.info("Weather data cache cleared due to city deletion");
    }


    public List<City> getCitiesByWeatherDataDate(LocalDate date) {
        List<City> cachedCities = weatherDataCache.getCitiesFromCache(date);
        if (cachedCities != null && !cachedCities.isEmpty()) {
            logger.info("Cache was used for date: {}", date);
            requestCounterService.incrementCounterByDate(date);
            return cachedCities;
        }
        List<City> cities = cityRepository.findCitiesByWeatherDataDate(date); // Этот метод возвращает все города вместе с погодными данными за указанную дату
        cities.forEach(city -> city.getWeatherDataList().removeIf(weatherData -> !weatherData.getDate().isEqual(date))); // Удаляем из списка погодных данных все записи, кроме тех, что имеют указанную дату
        logger.info("Database was used for date: {}", date);
        weatherDataCache.addToCache(date, cities);
        requestCounterService.incrementCounterByDate(date);
        return cities;
    }



}
