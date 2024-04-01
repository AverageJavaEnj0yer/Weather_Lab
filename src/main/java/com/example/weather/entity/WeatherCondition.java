package com.example.weather.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class WeatherCondition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String main;
    private String description;
    private String icon;
    //@JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)

    @JoinTable(
            name = "weather_data_weather_condition",
            joinColumns = @JoinColumn(name = "weatherCondition_id"),
            inverseJoinColumns = @JoinColumn(name = "weatherData_id")
    )
    @JsonBackReference

    private List<WeatherData> weatherDataList = new ArrayList<>();

    public WeatherCondition() {
    }

    public WeatherCondition(String main, String description, String icon) {
        this.main = main;
        this.description = description;
        this.icon = icon;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<WeatherData> getWeatherDataList() {
        return weatherDataList;
    }

    public void setWeatherDataList(List<WeatherData> weatherDataList) {
        this.weatherDataList = weatherDataList;
    }
}
