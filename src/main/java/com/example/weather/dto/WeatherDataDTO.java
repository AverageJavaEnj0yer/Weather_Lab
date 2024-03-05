package com.example.weather.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class WeatherDataDTO {
    private Long id;
    private LocalDate date;
    private double temperature;
    private double humidity;
    private Long cityId; // Optionally, you can include cityId to represent the association

    // Constructors, getters, setters...

    public WeatherDataDTO() {
    }

    public WeatherDataDTO(Long id, LocalDate date, double temperature, double humidity, Long cityId) {
        this.id = id;
        this.date = date;
        this.temperature = temperature;
        this.humidity = humidity;
        this.cityId = cityId;
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }
}
