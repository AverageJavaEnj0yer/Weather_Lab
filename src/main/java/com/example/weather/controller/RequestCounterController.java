package com.example.weather.controller;

import com.example.weather.service.RequestCounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/counter")
public class RequestCounterController {

    private final RequestCounterService requestCounterService;

    @Autowired
    public RequestCounterController(RequestCounterService requestCounterService) {
        this.requestCounterService = requestCounterService;
    }

    @GetMapping("/by-date")
    public Map<LocalDate, Long> getRequestCountByDate() {
        return requestCounterService.getRequestCountByDate();
    }

    @GetMapping("/total")
    public long getTotalRequestCount() {
        return requestCounterService.getTotalRequestCount();
    }
}
