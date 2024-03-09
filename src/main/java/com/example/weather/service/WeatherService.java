package com.example.weather.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.example.weather.entity.WeatherData;
import com.example.weather.repository.WeatherDataRepository;


@Service
public class WeatherService {

    private final WeatherDataRepository weatherDataRepository;

    @Value("${OpenWeatherMap.api.key}")
    private String apiKey;

    private static final String WEATHER_URL_BY_CITY = "https://api.openweathermap.org/data/2.5/weather?q={city}&appid={apiKey}";
    private static final String WEATHER_URL_BY_COORDINATES = "https://api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&appid={apiKey}";

    public WeatherService(WeatherDataRepository weatherDataRepository) {
        this.weatherDataRepository = weatherDataRepository;
    }

    public WeatherApiResponse getWeatherByCity(String city) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(WEATHER_URL_BY_CITY, WeatherApiResponse.class, city, apiKey);
    }

    public Object getWeatherByCoordinates(String lat, String lon) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(WEATHER_URL_BY_COORDINATES, Object.class, lat, lon, apiKey);
    }

    public void createWeatherData(WeatherData weatherData) {
        weatherDataRepository.save(weatherData);
    }
}
