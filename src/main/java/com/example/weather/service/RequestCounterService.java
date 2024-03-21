package com.example.weather.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RequestCounterService {

    private final Map<LocalDate, Long> requestCountByDate = new ConcurrentHashMap<>();
    private long totalRequestCount = 0;

    public void incrementCounterByDate(LocalDate date) {
        requestCountByDate.merge(date, 1L, Long::sum);
        totalRequestCount++;
    }

    public Map<LocalDate, Long> getRequestCountByDate() {
        return requestCountByDate;
    }

    public long getTotalRequestCount() {
        return totalRequestCount;
    }
}
