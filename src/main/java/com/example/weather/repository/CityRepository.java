package com.example.weather.repository;

import com.example.weather.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);

    City findByLonAndLat(Double lon, Double lat);

    City findByName(String name);

    @Query("SELECT DISTINCT c FROM City c JOIN c.weatherDataList wd WHERE wd.date = :date")
    List<City> findCitiesByWeatherDataDate(@Param("date") LocalDate date);
}

