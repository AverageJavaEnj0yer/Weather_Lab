package com.example.weather.service;

import com.example.weather.entity.WeatherData;
import com.example.weather.repository.WeatherDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class WeatherDataService {
    private final WeatherDataRepository weatherDataRepository;
    private static final Logger logger = LoggerFactory.getLogger(WeatherDataService.class);

    public WeatherDataService(WeatherDataRepository weatherDataRepository) {
        this.weatherDataRepository = weatherDataRepository;
    }

    public List<WeatherData> getAllWeatherData() {
        logger.info("Fetching all weather data");
        return weatherDataRepository.findAll();
    }

    public WeatherData getWeatherDataById(Long id) {
        logger.info("Fetching weather data by ID: {}", id);
        return weatherDataRepository.findById(id).orElse(null);
    }

    public WeatherData createWeatherData(WeatherData weatherData) {
        logger.info("Creating weather data");
        return weatherDataRepository.save(weatherData);
    }

    public WeatherData updateWeatherData(Long id, WeatherData newWeatherData) {
        logger.info("Updating weather data with ID: {}", id);
        WeatherData weatherDataToUpdate = weatherDataRepository.findById(id).orElse(null);
        if (weatherDataToUpdate != null) {
            weatherDataToUpdate.setDate(newWeatherData.getDate());
            weatherDataToUpdate.setTemperature(newWeatherData.getTemperature());
            weatherDataToUpdate.setHumidity(newWeatherData.getHumidity());
            return weatherDataRepository.save(weatherDataToUpdate);
        }
        return null;
    }

    public void deleteWeatherData(Long id) {
        logger.info("Deleting weather data with ID: {}", id);
        weatherDataRepository.deleteById(id);
    }
}
