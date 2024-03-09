package com.example.weather.cache;

import com.example.weather.entity.City;

import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WeatherDataCache {
    private static final int maxCacheSize = 1; // Максимальное количество записей в кэше
    private final Map<LocalDate, List<City>> cache = new ConcurrentHashMap<>();
    private final LinkedList<LocalDate> accessOrder = new LinkedList<>();

    public synchronized void addToCache(LocalDate date, List<City> cities) {
        if (cache.size() >= maxCacheSize) {
            LocalDate oldestDate = accessOrder.pollFirst();
            cache.remove(oldestDate);
        }
        cache.put(date, cities);
        accessOrder.addLast(date);
    }

    public List<City> getCitiesFromCache(LocalDate date) {
        return cache.getOrDefault(date, new ArrayList<>());
    }

    public void clearCache() {
        cache.clear();
        accessOrder.clear();
    }
}
