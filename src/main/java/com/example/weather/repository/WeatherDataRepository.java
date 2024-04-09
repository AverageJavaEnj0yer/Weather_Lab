package com.example.weather.repository;

import com.example.weather.entity.WeatherData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.weather.entity.City;
import java.time.LocalDate;


@Repository
public interface WeatherDataRepository extends JpaRepository<WeatherData, Long> {
    WeatherData findByDateAndTemperatureAndHumidityAndCity(LocalDate date, Double temperature, Double humidity, City city);

    void deleteByCityId(Long cityId);
}


