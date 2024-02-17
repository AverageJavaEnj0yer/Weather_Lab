package com.example.weather;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class WeatherApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeatherApplication.class, args);
    }

    @RestController
    public static class WeatherController {

        private static final String WEATHER_URL_BY_CITY = "https://api.openweathermap.org/data/2.5/weather?q={city}&appid={apiKey}";
        private static final String WEATHER_URL_BY_COORDINATES = "https://api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&appid={apiKey}";

        @GetMapping("/weather")
        public String getWeatherByCity(@RequestParam String city) {
            RestTemplate restTemplate = new RestTemplate();
            String apiKey = "71f05c123c7e6404aea1d9abf558f5db";
            return restTemplate.getForObject(WEATHER_URL_BY_CITY, String.class, city, apiKey);
        }

        @GetMapping("/weatherByCoordinates")
        public String getWeatherByCoordinates(@RequestParam String lat, @RequestParam String lon) {
            RestTemplate restTemplate = new RestTemplate();
            String apiKey = "71f05c123c7e6404aea1d9abf558f5db";
            return restTemplate.getForObject(WEATHER_URL_BY_COORDINATES, String.class, lat, lon, apiKey);
        }
    }
}
