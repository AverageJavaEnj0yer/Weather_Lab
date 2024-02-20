package com.example.weather;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class WeatherController {

    @Value("${openweathermap.api.key}")
    private String apiKey;

    private static final String WEATHER_URL_BY_CITY = "https://api.openweathermap.org/data/2.5/weather?q={city}&appid={apiKey}";
    private static final String WEATHER_URL_BY_COORDINATES = "https://api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&appid={apiKey}";

    @GetMapping("/weather")
    public String getWeatherByCity(@RequestParam String city) {
        RestTemplate restTemplate = new RestTemplate();
        String apiResponse = restTemplate.getForObject(WEATHER_URL_BY_CITY, String.class, city, apiKey);
        return "Это ваш прогноз погоды: " + apiResponse;
    }

    @GetMapping("/weatherByCoordinates")
    public String getWeatherByCoordinates(@RequestParam String lat, @RequestParam String lon) {
        RestTemplate restTemplate = new RestTemplate();
        String apiResponse = restTemplate.getForObject(WEATHER_URL_BY_COORDINATES, String.class, lat, lon, apiKey);
        return "Это ваш прогноз погоды: " + apiResponse;
    }
}

