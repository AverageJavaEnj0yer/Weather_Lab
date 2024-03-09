package com.example.weather.service;

import com.example.weather.entity.WeatherCondition;
import com.example.weather.repository.WeatherConditionRepository;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class WeatherConditionService {
    private final WeatherConditionRepository weatherConditionRepository;

    public WeatherConditionService(WeatherConditionRepository weatherConditionRepository) {
        this.weatherConditionRepository = weatherConditionRepository;
    }

    public List<WeatherCondition> getAllWeatherConditions() {
        return weatherConditionRepository.findAll();
    }

    public WeatherCondition findByMainAndDescription(String main, String description) {
        return weatherConditionRepository.findByMainAndDescription(main, description);
    }

    public WeatherCondition getWeatherConditionById(Long id) {
        Optional<WeatherCondition> weatherCondition = weatherConditionRepository.findById(id);
        return weatherCondition.orElse(null);
    }

    public WeatherCondition createWeatherCondition(WeatherCondition weatherCondition) {
        return weatherConditionRepository.save(weatherCondition);
    }

    public WeatherCondition updateWeatherCondition(Long id, WeatherCondition newWeatherConditionData) {
        Optional<WeatherCondition> optionalWeatherCondition = weatherConditionRepository.findById(id);
        if (optionalWeatherCondition.isPresent()) {
            WeatherCondition weatherConditionToUpdate = optionalWeatherCondition.get();
            weatherConditionToUpdate.setDescription(newWeatherConditionData.getDescription());
            // Другие поля для обновления...
            return weatherConditionRepository.save(weatherConditionToUpdate);
        }
        return null;
    }

    public void deleteWeatherCondition(Long id) {
        weatherConditionRepository.deleteById(id);
    }
}
