package com.example.weather;

import com.example.weather.controller.WeatherController;
import com.example.weather.model.Coordinates;
import com.example.weather.model.WeatherApiResponse;
import com.example.weather.model.WeatherConditionResponse;
import com.example.weather.repository.WeatherDataRepository;
import com.example.weather.service.CityService;
import com.example.weather.service.WeatherConditionService;
import com.example.weather.service.WeatherService;
import com.example.weather.model.MainData;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class WeatherControllerTest {

    @Mock
    private WeatherService weatherService;

    @Mock
    private CityService cityService;

    @Mock
    private WeatherConditionService weatherConditionService;

    @Mock
    private WeatherDataRepository weatherDataRepository;

    @InjectMocks
    private WeatherController weatherController;

    @Test
    public void testGetWeatherByCity() {
        // Arrange
        String cityName = "London";
        WeatherApiResponse apiResponse = new WeatherApiResponse();
        apiResponse.setName(cityName);

        MainData mainData = new MainData();
        mainData.setTemperature(20.0); // Установка температуры для фиктивных данных
        apiResponse.setMain(mainData);

        Coordinates coordinates = new Coordinates();
        coordinates.setLat(51.5074); // Установка фиктивной широты
        coordinates.setLon(-0.1278); // Установка фиктивной долготы
        apiResponse.setCoordinates(coordinates);

        List<WeatherConditionResponse> weatherConditions = new ArrayList<>();
        WeatherConditionResponse condition1 = new WeatherConditionResponse();
        condition1.setMain("Sunny");
        condition1.setDescription("Clear sky");
        weatherConditions.add(condition1);
        apiResponse.setWeather(weatherConditions);

        when(weatherService.getWeatherByCity(eq(cityName))).thenReturn(apiResponse);

        // Act
        ResponseEntity<Object> response = weatherController.getWeatherByCity(cityName);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(apiResponse, response.getBody());
        verify(weatherService, times(1)).getWeatherByCity(eq(cityName));
        verify(cityService, times(1)).createCity(any());
        verify(weatherConditionService, times(1)).createWeatherCondition(any());
    }



    @Test
    public void testGetWeatherByCoordinates() {
        // Arrange
        String lat = "51.5074";
        String lon = "0.1278";

        // Создаем тестовые данные для ответа от API
        WeatherApiResponse apiResponse = new WeatherApiResponse();
        Coordinates coordinates = new Coordinates();
        coordinates.setLat(51.5074);
        coordinates.setLon(0.1278);
        apiResponse.setCoordinates(coordinates);

        MainData mainData = new MainData();
        mainData.setTemperature(20.0); // устанавливаем температуру
        apiResponse.setMain(mainData);

        // Создаем фиктивные данные для погодных условий
        List<WeatherConditionResponse> weatherConditions = new ArrayList<>();
        WeatherConditionResponse condition1 = new WeatherConditionResponse();
        condition1.setMain("Sunny");
        condition1.setDescription("Clear sky");
        weatherConditions.add(condition1);
        apiResponse.setWeather(weatherConditions);

        // Подготавливаем мок сервиса WeatherService
        when(weatherService.getWeatherByCoordinates(anyString(), anyString())).thenReturn(apiResponse);

        // Act
        ResponseEntity<Object> response = weatherController.getWeatherByCoordinates(lat, lon);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(apiResponse, response.getBody());
        verify(weatherService, times(1)).getWeatherByCoordinates(eq(lat), eq(lon));
        verify(cityService, times(1)).createCity(any());
        verify(weatherConditionService, times(1)).createWeatherCondition(any());

    }


}
