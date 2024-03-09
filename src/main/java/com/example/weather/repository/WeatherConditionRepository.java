package com.example.weather.repository;

import com.example.weather.entity.WeatherCondition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherConditionRepository extends JpaRepository<WeatherCondition, Long> {
    boolean existsByMainAndDescription(String main, String description);
    WeatherCondition findByMainAndDescription(String main, String description); // Добавленный метод для поиска погодного условия по основной части и описанию
}

