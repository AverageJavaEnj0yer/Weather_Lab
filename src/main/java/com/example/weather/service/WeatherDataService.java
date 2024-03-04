package com.example.weather.service;

import com.example.weather.entity.WeatherData;
import com.example.weather.repository.WeatherDataRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class WeatherDataService {

    private WeatherDataRepository weatherDataRepository;

    public List<WeatherData> getAllWeatherData() {
        return weatherDataRepository.findAll();
    }

    public WeatherData getWeatherDataById(Long id) {
        return weatherDataRepository.findById(id).orElse(null);
    }

    public WeatherData createWeatherData(WeatherData weatherData) {
        return weatherDataRepository.save(weatherData);
    }

    public WeatherData updateWeatherData(Long id, WeatherData newWeatherData) {
        WeatherData weatherDataToUpdate = weatherDataRepository.findById(id).orElse(null);
        if (weatherDataToUpdate != null) {
            weatherDataToUpdate.setDate(newWeatherData.getDate());
            weatherDataToUpdate.setTemperature(newWeatherData.getTemperature());
            weatherDataToUpdate.setHumidity(newWeatherData.getHumidity());
            // Другие поля для обновления...
            return weatherDataRepository.save(weatherDataToUpdate);
        }
        return null;
    }

    public void deleteWeatherData(Long id) {
        weatherDataRepository.deleteById(id);
    }
}
