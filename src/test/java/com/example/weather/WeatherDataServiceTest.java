package com.example.weather;

import com.example.weather.entity.WeatherData;
import com.example.weather.repository.WeatherDataRepository;
import com.example.weather.service.WeatherDataService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertNull;


class WeatherDataServiceTest {

   private WeatherDataService weatherDataService;
   private WeatherDataRepository weatherDataRepository;

   @BeforeEach
   void setUp() {
      weatherDataRepository = mock(WeatherDataRepository.class);
      weatherDataService = new WeatherDataService(weatherDataRepository);
   }

   @Test
   void testCreateWeatherData() {
      WeatherData testData = new WeatherData();
      when(weatherDataRepository.save(testData)).thenReturn(testData);

      WeatherData result = weatherDataService.createWeatherData(testData);

      assertNotNull(result);
      assertEquals(testData, result);
   }
   @Test
   void testUpdateWeatherData_WhenNotFound() {
      // Создаем тестовые данные
      Long weatherDataId = 1L;
      WeatherData newWeatherData = new WeatherData();

      // Устанавливаем поведение заглушки репозитория
      when(weatherDataRepository.findById(weatherDataId)).thenReturn(Optional.empty());

      // Вызываем метод, который тестируем
      WeatherData updatedWeatherData = weatherDataService.updateWeatherData(weatherDataId, newWeatherData);

      // Проверяем, что метод вернул null, так как данные для обновления не найдены
      assertNull(updatedWeatherData);
      // Проверяем, что метод findById был вызван ровно 1 раз с правильным id
      verify(weatherDataRepository, times(1)).findById(weatherDataId);
   }

   @Test
   void testUpdateWeatherData() {
      WeatherData testData = new WeatherData();
      testData.setId(1L);
      when(weatherDataRepository.findById(1L)).thenReturn(Optional.of(testData));
      when(weatherDataRepository.save(testData)).thenReturn(testData);

      WeatherData newData = new WeatherData();
      newData.setDate(LocalDate.now());
      newData.setTemperature(25.0);
      newData.setHumidity(60.0);

      when(weatherDataRepository.save(testData)).thenReturn(newData); // Возвращаем обновленные данные

      WeatherData result = weatherDataService.updateWeatherData(1L, newData);

      assertNotNull(result); // Проверяем, что объект не null
      assertEquals(newData.getTemperature(), result.getTemperature(), 0.01);
      assertEquals(newData.getDate(), result.getDate());
      assertEquals(newData.getHumidity(), result.getHumidity(), 0.01);
   }



   @Test
   void testDeleteWeatherData() {
      WeatherData testData = new WeatherData();
      testData.setId(1L);
      doNothing().when(weatherDataRepository).deleteById(1L);

      weatherDataService.deleteWeatherData(1L);

      // Проверка, что метод deleteById вызывался один раз с аргументом 1L
      verify(weatherDataRepository, times(1)).deleteById(1L);
   }

   @Test
   void testGetWeatherDataById() {
      WeatherData testData = new WeatherData();
      testData.setId(1L);
      when(weatherDataRepository.findById(1L)).thenReturn(Optional.of(testData));

      WeatherData result = weatherDataService.getWeatherDataById(1L);

      assertNotNull(result);
      assertEquals(testData, result);
   }

   @Test
   void testGetAllWeatherData() {
      WeatherData testData1 = new WeatherData();
      WeatherData testData2 = new WeatherData();
      when(weatherDataRepository.findAll()).thenReturn(List.of(testData1, testData2));

      List<WeatherData> result = weatherDataService.getAllWeatherData();

      assertNotNull(result);
      assertEquals(2, result.size());
   }
}

