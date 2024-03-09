package com.example.weather.service;

import com.example.weather.entity.City;
import com.example.weather.repository.CityRepository;
import com.example.weather.exception.CityAlreadyExistsException;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import com.example.weather.cache.WeatherDataCache;


@Service
public class CityService {
    private final CityRepository cityRepository;
    private final WeatherDataCache weatherDataCache;

    public CityService(CityRepository cityRepository, WeatherDataCache weatherDataCache) {
        this.cityRepository = cityRepository;
        this.weatherDataCache = weatherDataCache;
    }

    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    public City getCityById(Long id) {
        return cityRepository.findById(id).orElse(null);
    }

    public City getCityByLonAndLat(Double lon, Double lat) {
        return cityRepository.findByLonAndLat(lon, lat);
    }

    public City createCity(City city) {
        if (cityRepository.existsByName(city.getName())) {
            throw new CityAlreadyExistsException("Город с таким именем уже существует");
        }
        return cityRepository.save(city);
    }

    public City updateCity(Long id, City newCityData) {
        City cityToUpdate = cityRepository.findById(id).orElse(null);
        if (cityToUpdate != null) {
            if (cityRepository.existsByNameAndIdNot(newCityData.getName(), id)) {
                throw new CityAlreadyExistsException("Город с таким именем уже существует");
            }
            cityToUpdate.setName(newCityData.getName());
            cityToUpdate.setLon(newCityData.getLon()); // Update longitude
            cityToUpdate.setLat(newCityData.getLat()); // Update latitude
            // Update other fields as needed...
            return cityRepository.save(cityToUpdate);
        }
        return null;
    }

    public void deleteCity(Long id) {
        cityRepository.deleteById(id);
    }

    public List<City> getCitiesByWeatherDataDate(LocalDate date) {
        List<City> cachedCities = weatherDataCache.getCitiesFromCache(date);
        if (!cachedCities.isEmpty()) {
            System.out.println("Cache was used " + date);
            return cachedCities;
        }
        List<City> cities = cityRepository.findCitiesByWeatherDataDate(date);
        weatherDataCache.addToCache(date, cities);
        return cities;
    }
}
