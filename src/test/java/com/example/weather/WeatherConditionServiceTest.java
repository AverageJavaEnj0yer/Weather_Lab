package com.example.weather;

import com.example.weather.entity.WeatherCondition;
import com.example.weather.repository.WeatherConditionRepository;
import com.example.weather.service.WeatherConditionService;
import com.example.weather.cache.WeatherDataCache;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.doNothing;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertNull;

import static org.junit.jupiter.api.Assertions.assertNotNull;



import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class WeatherConditionServiceTest {

    @Mock
    private WeatherConditionRepository weatherConditionRepository;

    @Mock
    private WeatherDataCache weatherDataCache;

    @InjectMocks
    private WeatherConditionService weatherConditionService;


    @Test
    void testGetWeatherConditionById() {
        WeatherCondition expectedCondition = new WeatherCondition("Clouds", "Some clouds in the sky", "01d");
        expectedCondition.setId(1L);
        when(weatherConditionRepository.findById(1L)).thenReturn(Optional.of(expectedCondition));

        WeatherCondition actualCondition = weatherConditionService.getWeatherConditionById(1L);

        assertEquals(expectedCondition, actualCondition);
    }

    @Test
    void testCreateWeatherCondition() {
        WeatherCondition newCondition = new WeatherCondition("Rain", "Heavy rain", "01d");
        when(weatherConditionRepository.save(newCondition)).thenReturn(newCondition);

        WeatherCondition createdCondition = weatherConditionService.createWeatherCondition(newCondition);

        assertEquals(newCondition, createdCondition);
    }
    @Test
    void testGetAllWeatherConditions() {
        // Создаем список тестовых условий
        List<WeatherCondition> expectedConditions = List.of(
                new WeatherCondition("Clouds", "Some clouds in the sky", "01d"),
                new WeatherCondition("Rain", "Heavy rain", "01d"),
                new WeatherCondition("Snow", "Heavy snowfall", "s01d")
        );

        // Устанавливаем поведение заглушки репозитория
        when(weatherConditionRepository.findAll()).thenReturn(expectedConditions);

        // Вызываем метод, который тестируем
        List<WeatherCondition> actualConditions = weatherConditionService.getAllWeatherConditions();

        // Проверяем, что метод вернул ожидаемый список WeatherCondition
        assertEquals(expectedConditions.size(), actualConditions.size());
        assertEquals(expectedConditions, actualConditions);
    }
    @Test
    void testFindByMainAndDescription() {
        // Создаем тестовый объект WeatherCondition
        WeatherCondition expectedCondition = new WeatherCondition("Clouds", "Some clouds in the sky", "01d");
        // Подготавливаем поведение заглушки репозитория
        when(weatherConditionRepository.findByMainAndDescription("Clouds", "Some clouds in the sky")).thenReturn(expectedCondition);

        // Вызываем метод, который тестируем
        WeatherCondition actualCondition = weatherConditionService.findByMainAndDescription("Clouds", "Some clouds in the sky");

        // Проверяем, что вернулся ожидаемый объект WeatherCondition
        assertEquals(expectedCondition, actualCondition);
    }
    @Test
    void testUpdateWeatherCondition() {
        WeatherCondition existingCondition = new WeatherCondition("Snow", "Heavy snowfall", "s01d");
        existingCondition.setId(1L);
        WeatherCondition updatedConditionData = new WeatherCondition("Snow", "Light snowfall", "01d");
        when(weatherConditionRepository.findById(1L)).thenReturn(Optional.of(existingCondition));
        when(weatherConditionRepository.save(existingCondition)).thenReturn(updatedConditionData); // Возвращаем обновленные данные

        WeatherCondition updatedCondition = weatherConditionService.updateWeatherCondition(1L, updatedConditionData);

        assertNotNull(updatedCondition); // Проверяем, что объект не null
        assertEquals(updatedConditionData.getDescription(), updatedCondition.getDescription());
    }

    @Test
    void testDeleteWeatherCondition() {
        // Arrange
        WeatherCondition existingCondition = new WeatherCondition("Sunny", "Clear skies", "01d");
        existingCondition.setId(1L);
        doNothing().when(weatherConditionRepository).deleteById(1L);

        // Act
        weatherConditionService.deleteWeatherCondition(1L);

        // Assert
        verify(weatherConditionRepository, times(1)).deleteById(1L);
    }


}
