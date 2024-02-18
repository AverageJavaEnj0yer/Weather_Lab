package com.example.weather;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WeatherApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testGetWeatherByCity() {
        ResponseEntity<String> response = restTemplate.getForEntity("/weather?city=London", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("прогноз погоды");
    }

    @Test
    void testGetWeatherByCoordinates() {
        ResponseEntity<String> response = restTemplate.getForEntity("/weatherByCoordinates?lat=51.5074&lon=0.1278", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("прогноз погоды");
    }
}
