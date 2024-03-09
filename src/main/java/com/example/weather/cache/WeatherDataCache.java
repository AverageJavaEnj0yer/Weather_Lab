package com.example.weather.cache;

import com.example.weather.entity.City;

import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WeatherDataCache {
    private final Map<LocalDate, List<City>> cache = new ConcurrentHashMap<>();

    public void addToCache(LocalDate date, List<City> cities) {
        cache.put(date, cities);
    }

    public List<City> getCitiesFromCache(LocalDate date) {
        return cache.getOrDefault(date, new ArrayList<>());
    }

    public void clearCache() {
        cache.clear();
    }
}

