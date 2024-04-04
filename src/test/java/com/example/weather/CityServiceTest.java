package com.example.weather;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.example.weather.cache.WeatherDataCache;
import com.example.weather.entity.City;
import com.example.weather.repository.CityRepository;
import com.example.weather.service.CityService;
import com.example.weather.exception.CityAlreadyExistsException;
import com.example.weather.service.RequestCounterService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import java.util.Optional;

import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CityServiceTest {

    @Mock
    private CityRepository cityRepository;

    @Mock
    private WeatherDataCache weatherDataCache;

    @Mock
    private RequestCounterService requestCounterService;

    @InjectMocks
    private CityService cityService;

    @BeforeEach
    void setUp() {
        // Здесь можно добавить предварительную настройку тестовых данных, если это необходимо
    }

    @Test
    void testFindByName() {
        // Создаем тестовые данные
        City testCity = new City("TestCity", 0.0, 0.0);
        // Устанавливаем поведение заглушки репозитория
        when(cityRepository.findByName("TestCity")).thenReturn(testCity);

        // Вызываем метод, который тестируем
        City foundCity = cityService.findByName("TestCity");

        // Проверяем, что результат соответствует ожидаемому
        assertEquals(testCity, foundCity);
    }

    @Test
    void testGetAllCities() {
        // Создаем тестовые данные
        List<City> testCities = new ArrayList<>();
        testCities.add(new City("City1", 0.0, 0.0));
        testCities.add(new City("City2", 0.0, 0.0));
        // Устанавливаем поведение заглушки репозитория
        when(cityRepository.findAll()).thenReturn(testCities);

        // Вызываем метод, который тестируем
        List<City> result = cityService.getAllCities();

        // Проверяем, что результат соответствует ожидаемому
        assertEquals(testCities, result);
        // Проверяем, что метод findAll был вызван ровно 1 раз
        verify(cityRepository, times(1)).findAll();
    }

    @Test
    void testFindByName_WhenCityNotFound() {
        // Устанавливаем поведение заглушки репозитория
        when(cityRepository.findByName("NonExistentCity")).thenReturn(null);

        // Вызываем метод, который тестируем
        City foundCity = cityService.findByName("NonExistentCity");

        // Проверяем, что результат равен null
        assertNull(foundCity);
    }

    // Тесты для методов getAllCities, getCityById, getCityByLonAndLat и getCitiesByWeatherDataDate аналогичны и не приведены здесь для краткости.

    @Test
    void testCreateCity_WhenCityDoesNotExist() {
        // Создаем тестовые данные
        City testCity = new City("TestCity", 0.0, 0.0);
        // Устанавливаем поведение заглушки репозитория
        when(cityRepository.existsByName("TestCity")).thenReturn(false);
        when(cityRepository.save(testCity)).thenReturn(testCity);

        // Вызываем метод, который тестируем
        City createdCity = cityService.createCity(testCity);

        // Проверяем, что результат соответствует ожидаемому
        assertEquals(testCity, createdCity);
        // Проверяем, что метод save был вызван ровно 1 раз
        verify(cityRepository, times(1)).save(testCity);
    }

    @Test
    void testGetCityByLonAndLat() {
        // Создаем тестовые данные
        Double lon = 10.0;
        Double lat = 20.0;
        City testCity = new City("TestCity", lon, lat);
        // Устанавливаем поведение заглушки репозитория
        when(cityRepository.findByLonAndLat(lon, lat)).thenReturn(testCity);

        // Вызываем метод, который тестируем
        City result = cityService.getCityByLonAndLat(lon, lat);

        // Проверяем, что результат соответствует ожидаемому
        assertEquals(testCity, result);
        // Проверяем, что метод findByLonAndLat был вызван ровно 1 раз с правильными параметрами
        verify(cityRepository, times(1)).findByLonAndLat(lon, lat);
    }

    @Test
    void testGetCityById_WhenCityExists() {
        // Создаем тестовые данные
        Long cityId = 1L;
        City testCity = new City("TestCity", 0.0, 0.0);
        // Устанавливаем поведение заглушки репозитория
        when(cityRepository.findById(cityId)).thenReturn(Optional.of(testCity));

        // Вызываем метод, который тестируем
        City result = cityService.getCityById(cityId);

        // Проверяем, что результат соответствует ожидаемому
        assertEquals(testCity, result);
        // Проверяем, что метод findById был вызван ровно 1 раз с правильным id
        verify(cityRepository, times(1)).findById(cityId);
    }

    @Test
    void testGetCityById_WhenCityDoesNotExist() {
        // Создаем тестовые данные
        Long cityId = 1L;
        // Устанавливаем поведение заглушки репозитория
        when(cityRepository.findById(cityId)).thenReturn(Optional.empty());

        // Вызываем метод, который тестируем
        City result = cityService.getCityById(cityId);

        // Проверяем, что результат равен null, так как города с таким id не существует
        assertNull(result);
        // Проверяем, что метод findById был вызван ровно 1 раз с правильным id
        verify(cityRepository, times(1)).findById(cityId);
    }

    @Test
    void testCreateBulkCities() {
        // Создаем тестовые данные
        List<City> testCities = new ArrayList<>();
        testCities.add(new City("City1", 0.0, 0.0));
        testCities.add(new City("City2", 0.0, 0.0));
        testCities.add(new City("City3", 0.0, 0.0));
        // Устанавливаем поведение заглушки репозитория
        when(cityRepository.existsByName("City1")).thenReturn(false);
        when(cityRepository.existsByName("City2")).thenReturn(true);
        when(cityRepository.existsByName("City3")).thenReturn(false);
        when(cityRepository.save(any(City.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Вызываем метод, который тестируем
        List<City> createdCities = cityService.createBulkCities(testCities);

        // Проверяем, что результат соответствует ожидаемому (должны быть добавлены только города, которые еще не существуют)
        assertEquals(2, createdCities.size());
        assertTrue(createdCities.contains(testCities.get(0)));
        assertTrue(createdCities.contains(testCities.get(2)));
        // Проверяем, что метод save был вызван только для городов, которые еще не существуют
        verify(cityRepository, times(1)).save(testCities.get(0));
        verify(cityRepository, times(0)).save(testCities.get(1));
        verify(cityRepository, times(1)).save(testCities.get(2));
    }
    @Test
    void testCreateCity_WhenCityAlreadyExists() {
        // Создаем тестовые данные
        City testCity = new City("TestCity", 0.0, 0.0);
        // Устанавливаем поведение заглушки репозитория
        when(cityRepository.existsByName("TestCity")).thenReturn(true);

        // Проверяем, что выбрасывается ожидаемое исключение
        assertThrows(CityAlreadyExistsException.class, () -> {
            cityService.createCity(testCity);
        });
        // Проверяем, что метод save не был вызван
        verify(cityRepository, never()).save(any());
    }



    @Test
    void testUpdateCity_WhenNewCityNameAlreadyExists() {
        // Создаем тестовые данные
        Long cityId = 1L;
        City existingCity = new City("ExistingCity", 0.0, 0.0);
        City newCityData = new City("ExistingCity", 10.0, 20.0);
        City updatedCityData = new City("UpdatedCity", 10.0, 20.0);
        // Устанавливаем поведение заглушки репозитория
        when(cityRepository.findById(cityId)).thenReturn(Optional.of(existingCity));
        when(cityRepository.existsByNameAndIdNot(newCityData.getName(), cityId)).thenReturn(true);

        // Проверяем, что метод бросает исключение, если новое имя города уже существует
        assertThrows(CityAlreadyExistsException.class, () -> {
            cityService.updateCity(cityId, newCityData);
        });
        // Проверяем, что метод findById был вызван ровно 1 раз с правильным id
        verify(cityRepository, times(1)).findById(cityId);
        // Проверяем, что метод existsByNameAndIdNot был вызван ровно 1 раз с правильными параметрами
        verify(cityRepository, times(1)).existsByNameAndIdNot(newCityData.getName(), cityId);
        // Проверяем, что метод save не был вызван (город не обновлен из-за существующего имени)
        verify(cityRepository, never()).save(any());
        // Проверяем, что кэш не был очищен (город не обновлен из-за существующего имени)
        verify(weatherDataCache, never()).clearCache();
    }

    @Test
    void testDeleteCity_WhenCityExists() {
        // Создаем тестовые данные
        Long cityId = 1L;
        // Вызываем метод, который тестируем
        cityService.deleteCity(cityId);

        // Проверяем, что метод deleteById был вызван ровно 1 раз с правильным id
        verify(cityRepository, times(1)).deleteById(cityId);
        // Проверяем, что кэш был очищен
        verify(weatherDataCache, times(1)).clearCache();
    }
    @Test
    void testGetCitiesByWeatherDataDate_WhenCacheIsNotEmpty() {
        // Создаем тестовые данные
        LocalDate date = LocalDate.now();
        List<City> cachedCities = new ArrayList<>();
        cachedCities.add(new City("City1", 0.0, 0.0));
        cachedCities.add(new City("City2", 0.0, 0.0));
        // Устанавливаем поведение заглушки кэша
        when(weatherDataCache.getCitiesFromCache(date)).thenReturn(cachedCities);

        // Вызываем метод, который тестируем
        List<City> result = cityService.getCitiesByWeatherDataDate(date);

        // Проверяем, что метод вернул ожидаемый список городов из кэша
        assertEquals(cachedCities, result);
        // Проверяем, что счетчик запросов по дате был увеличен на 1
        verify(requestCounterService, times(1)).incrementCounterByDate(date);
    }

    @Test
    void testGetCitiesByWeatherDataDate_WhenCacheIsEmpty() {
        // Создаем тестовые данные
        LocalDate date = LocalDate.now();
        List<City> citiesFromDatabase = new ArrayList<>();
        citiesFromDatabase.add(new City("City1", 0.0, 0.0));
        citiesFromDatabase.add(new City("City2", 0.0, 0.0));
        // Устанавливаем поведение заглушки кэша
        when(weatherDataCache.getCitiesFromCache(date)).thenReturn(null);
        // Устанавливаем поведение заглушки репозитория
        when(cityRepository.findCitiesByWeatherDataDate(date)).thenReturn(citiesFromDatabase);

        // Вызываем метод, который тестируем
        List<City> result = cityService.getCitiesByWeatherDataDate(date);

        // Проверяем, что метод вернул ожидаемый список городов из базы данных
        assertEquals(citiesFromDatabase, result);
        // Проверяем, что счетчик запросов по дате был увеличен на 1
        verify(requestCounterService, times(1)).incrementCounterByDate(date);
        // Проверяем, что метод addToCache был вызван с правильными параметрами
        verify(weatherDataCache, times(1)).addToCache(date, citiesFromDatabase);
    }
}


