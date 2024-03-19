package com.example.weather;

import com.example.weather.entity.City;
import com.example.weather.service.CityService;
import com.example.weather.exception.CityAlreadyExistsException;
import com.example.weather.cache.WeatherDataCache;
import com.example.weather.repository.CityRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CityServiceTest {

    @Mock
    private CityRepository cityRepository;

    @Mock
    private WeatherDataCache weatherDataCache;

    @InjectMocks
    private CityService cityService;

    @Test
    public void testGetAllCities() {
        // Setup
        List<City> expectedCities = new ArrayList<>();
        expectedCities.add(new City("City 1", 1.0, 2.0));
        expectedCities.add(new City("City 2", 3.0, 4.0));
        when(cityRepository.findAll()).thenReturn(expectedCities);

        // Test
        List<City> actualCities = cityService.getAllCities();

        // Verify
        assertEquals(expectedCities, actualCities);
    }

    @Test
    public void testGetCityById() {
        // Setup
        Long cityId = 1L;
        City expectedCity = new City("City 1", 1.0, 2.0);
        when(cityRepository.findById(cityId)).thenReturn(Optional.of(expectedCity));

        // Test
        City actualCity = cityService.getCityById(cityId);

        // Verify
        assertEquals(expectedCity, actualCity);
    }

    @Test
    public void testGetCityByLonAndLat() {
        // Setup
        Double lon = 1.0;
        Double lat = 2.0;
        City expectedCity = new City("City 1", lon, lat);
        when(cityRepository.findByLonAndLat(lon, lat)).thenReturn(expectedCity);

        // Test
        City actualCity = cityService.getCityByLonAndLat(lon, lat);

        // Verify
        assertEquals(expectedCity, actualCity);
    }

    @Test
    public void testCreateCity() {
        // Setup
        City cityToCreate = new City("New City", 1.0, 2.0);
        when(cityRepository.existsByName(cityToCreate.getName())).thenReturn(false);
        when(cityRepository.save(cityToCreate)).thenReturn(cityToCreate);

        // Test
        City createdCity = cityService.createCity(cityToCreate);

        // Verify
        assertEquals(cityToCreate, createdCity);
        verify(weatherDataCache, times(0)).clearCache(); // Verify cache not cleared for new city
    }

    @Test(expected = CityAlreadyExistsException.class)
    public void testCreateCityCityAlreadyExists() {
        // Setup
        City existingCity = new City("Existing City", 1.0, 2.0);
        when(cityRepository.existsByName(existingCity.getName())).thenReturn(true);

        // Test
        cityService.createCity(existingCity); // This should throw CityAlreadyExistsException
    }

    @Test
    public void testUpdateCity() {
        // Setup
        Long cityId = 1L;
        City existingCity = new City("Existing City", 1.0, 2.0);
        City updatedCityData = new City("Updated City", 3.0, 4.0);
        when(cityRepository.findById(cityId)).thenReturn(Optional.of(existingCity));
        when(cityRepository.existsByNameAndIdNot(updatedCityData.getName(), cityId)).thenReturn(false);
        when(cityRepository.save(existingCity)).thenReturn(existingCity);

        // Test
        City updatedCity = cityService.updateCity(cityId, updatedCityData);

        // Verify
        assertEquals(existingCity.getName(), updatedCity.getName());
        assertEquals(existingCity.getLon(), updatedCity.getLon());
        assertEquals(existingCity.getLat(), updatedCity.getLat());
        verify(weatherDataCache, times(1)).clearCache(); // Verify cache cleared due to city update
    }

    @Test(expected = CityAlreadyExistsException.class)
    public void testUpdateCityCityAlreadyExists() {
        // Setup
        Long cityId = 1L;
        City existingCity = new City("Existing City", 1.0, 2.0);
        City updatedCityData = new City("Updated City", 3.0, 4.0);
        when(cityRepository.findById(cityId)).thenReturn(Optional.of(existingCity));
        when(cityRepository.existsByNameAndIdNot(updatedCityData.getName(), cityId)).thenReturn(true);

        // Test
        cityService.updateCity(cityId, updatedCityData); // This should throw CityAlreadyExistsException
    }

    @Test
    public void testDeleteCity() {
        // Setup
        Long cityId = 1L;

        // Test
        cityService.deleteCity(cityId);

        // Verify
        verify(cityRepository, times(1)).deleteById(cityId);
        verify(weatherDataCache, times(1)).clearCache(); // Verify cache cleared due to city deletion
    }

    @Test
    public void testGetCitiesByWeatherDataDate() {
        // Setup
        LocalDate date = LocalDate.now();
        List<City> expectedCities = new ArrayList<>();
        expectedCities.add(new City("City 1", 1.0, 2.0));
        expectedCities.add(new City("City 2", 3.0, 4.0));
        when(weatherDataCache.getCitiesFromCache(date)).thenReturn(expectedCities);

        // Test
        List<City> actualCities = cityService.getCitiesByWeatherDataDate(date);

        // Verify
        assertEquals(expectedCities, actualCities);
        verify(weatherDataCache, times(1)).getCitiesFromCache(date); // Verify cache used
    }

}
