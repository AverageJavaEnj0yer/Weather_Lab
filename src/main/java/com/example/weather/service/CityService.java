package com.example.weather.service;

import com.example.weather.entity.City;
import com.example.weather.repository.CityRepository;
import com.example.weather.exception.CityAlreadyExistsException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CityService {
    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
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
        return cityRepository.findCitiesByWeatherDataDate(date);
    }
}

