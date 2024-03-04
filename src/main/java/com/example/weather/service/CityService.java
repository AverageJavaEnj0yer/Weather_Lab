package com.example.weather.service;

import com.example.weather.entity.City;
import com.example.weather.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {
    @Autowired
    private CityRepository cityRepository;

    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    public City getCityById(Long id) {
        return cityRepository.findById(id).orElse(null);
    }

    public City createCity(City city) {
        // Проверяем, существует ли уже город с таким именем
        if (cityRepository.existsByName(city.getName())) {
            // Если город существует, можно выбросить исключение или вернуть null
            // Здесь выбрасывается исключение, но можно использовать и другие подходы
            throw new RuntimeException("Город с таким именем уже существует");
        }
        // Если город с таким именем не существует, сохраняем его
        return cityRepository.save(city);
    }

    public City updateCity(Long id, City newCityData) {
        City cityToUpdate = cityRepository.findById(id).orElse(null);
        if (cityToUpdate != null) {
            if (cityRepository.existsByNameAndIdNot(newCityData.getName(), id)) {
                throw new RuntimeException("Город с таким именем уже существует");
            }
            cityToUpdate.setName(newCityData.getName());
            // Другие поля для обновления...
            return cityRepository.save(cityToUpdate);
        }
        return null;
    }

    public void deleteCity(Long id) {
        cityRepository.deleteById(id);
    }
}
