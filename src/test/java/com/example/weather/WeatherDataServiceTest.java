package com.example.weather;

import com.example.weather.entity.WeatherData;
import com.example.weather.repository.WeatherDataRepository;
import com.example.weather.service.WeatherDataService;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertNotNull;


public class WeatherDataServiceTest {

    private WeatherDataService weatherDataService;
    private WeatherDataRepository weatherDataRepository;

    @BeforeEach
    public void setUp() {
        weatherDataRepository = mock(WeatherDataRepository.class);
        weatherDataService = new WeatherDataService(weatherDataRepository);
    }

    @Test
    public void testCreateWeatherData() {
        WeatherData testData = new WeatherData();
        when(weatherDataRepository.save(testData)).thenReturn(testData);

        WeatherData result = weatherDataService.createWeatherData(testData);

        assertNotNull(result);
        assertEquals(testData, result);
    }

    @Test
    public void testUpdateWeatherData() {
        WeatherData testData = new WeatherData();
        testData.setId(1L);
        when(weatherDataRepository.findById(1L)).thenReturn(Optional.of(testData));
        when(weatherDataRepository.save(testData)).thenReturn(testData);

        WeatherData newData = new WeatherData();
        newData.setTemperature(25.0);
        WeatherData result = weatherDataService.updateWeatherData(1L, newData);

        assertNotNull(result);
        assertEquals(newData.getTemperature(), result.getTemperature(), 0.01);
    }

    @Test
    public void testDeleteWeatherData() {
        WeatherData testData = new WeatherData();
        testData.setId(1L);
        doNothing().when(weatherDataRepository).deleteById(1L);

        weatherDataService.deleteWeatherData(1L);

    }
}
