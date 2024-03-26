package com.example.weather.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class WeatherData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private double temperature;
    private double humidity;

    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    @JsonBackReference
    private City city;

    //@JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)

    @JoinTable(
            name = "weather_data_weather_condition",
            joinColumns = @JoinColumn(name = "weatherData_id"),
            inverseJoinColumns = @JoinColumn(name = "weatherCondition_id")
    )
    private List<WeatherCondition> weatherConditions = new ArrayList<>();

    public WeatherData() {
    }

    public WeatherData(LocalDate date, double temperature, double humidity, City city) {
        this.date = date;
        this.temperature = temperature;
        this.humidity = humidity;
        this.city = city;
    }

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

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public List<WeatherCondition> getWeatherConditions() {
        return weatherConditions;
    }

    public void setWeatherConditions(List<WeatherCondition> weatherConditions) {
        this.weatherConditions = weatherConditions;
    }
}
