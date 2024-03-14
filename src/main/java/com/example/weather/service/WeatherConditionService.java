package com.example.weather.service;

import com.example.weather.entity.WeatherCondition;
import com.example.weather.repository.WeatherConditionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.example.weather.cache.WeatherDataCache;


import java.util.List;
import java.util.Optional;

@Service
public class WeatherConditionService {
    private final WeatherConditionRepository weatherConditionRepository;
    private final WeatherDataCache weatherDataCache;
    private static final Logger logger = LoggerFactory.getLogger(WeatherConditionService.class);

    public WeatherConditionService(WeatherConditionRepository weatherConditionRepository, WeatherDataCache weatherDataCache) {
        this.weatherConditionRepository = weatherConditionRepository;
        this.weatherDataCache = weatherDataCache;
    }

    public List<WeatherCondition> getAllWeatherConditions() {
        logger.info("Fetching all weather conditions");
        return weatherConditionRepository.findAll();
    }

    public WeatherCondition findByMainAndDescription(String main, String description) {
        logger.info("Fetching weather condition by main: {} and description: {}", main, description);
        return weatherConditionRepository.findByMainAndDescription(main, description);
    }

    public WeatherCondition getWeatherConditionById(Long id) {
        logger.info("Fetching weather condition by ID: {}", id);
        Optional<WeatherCondition> weatherCondition = weatherConditionRepository.findById(id);
        return weatherCondition.orElse(null);
    }

    public WeatherCondition createWeatherCondition(WeatherCondition weatherCondition) {
        logger.info("Creating weather condition");
        return weatherConditionRepository.save(weatherCondition);
    }

    public WeatherCondition updateWeatherCondition(Long id, WeatherCondition newWeatherConditionData) {
        logger.info("Updating weather condition with ID: {}", id);
        Optional<WeatherCondition> optionalWeatherCondition = weatherConditionRepository.findById(id);
        if (optionalWeatherCondition.isPresent()) {
            WeatherCondition weatherConditionToUpdate = optionalWeatherCondition.get();
            weatherConditionToUpdate.setDescription(newWeatherConditionData.getDescription());
            WeatherCondition updatedWeatherCondition = weatherConditionRepository.save(weatherConditionToUpdate);
            clearCacheIfWeatherConditionUpdated(updatedWeatherCondition); // Очистка кэша при обновлении
            return updatedWeatherCondition;
        }
        return null;
    }

    public void deleteWeatherCondition(Long id) {
        logger.info("Deleting weather condition with ID: {}", id);
        weatherConditionRepository.deleteById(id);
        clearCacheIfWeatherConditionDeleted(id); // Очистка кэша при удалении
    }

    private void clearCacheIfWeatherConditionUpdated(WeatherCondition updatedWeatherCondition) {
        if (updatedWeatherCondition != null) {
            weatherDataCache.clearCache();
            logger.info("Weather data cache cleared due to update in weather condition");
        }
    }

    private void clearCacheIfWeatherConditionDeleted(Long id) {
        weatherDataCache.clearCache();
        logger.info("Weather data cache cleared due to deletion of weather condition with ID: {}", id);
    }
}
