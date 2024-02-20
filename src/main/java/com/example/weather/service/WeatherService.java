package com.example.weather.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    @Value("${OpenWeatherMap.api.key}")
    private String apiKey;

    private static final String WEATHER_URL_BY_CITY = "https://api.openweathermap.org/data/2.5/weather?q={city}&appid={apiKey}";
    private static final String WEATHER_URL_BY_COORDINATES = "https://api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&appid={apiKey}";

    public Object getWeatherByCity(String city) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(WEATHER_URL_BY_CITY, Object.class, city, apiKey);
    }

    public Object getWeatherByCoordinates(String lat, String lon) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(WEATHER_URL_BY_COORDINATES, Object.class, lat, lon, apiKey);
    }
}
