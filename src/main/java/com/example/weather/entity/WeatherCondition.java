package com.example.weather.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class WeatherCondition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String main;
    private String description;
    private String icon;
    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "weatherdata_weathercondition",
            joinColumns = @JoinColumn(name = "weathercondition_id"),
            inverseJoinColumns = @JoinColumn(name = "weatherdata_id")
    )
    private List<WeatherData> weatherDataList = new ArrayList<>();

    // Constructors, getters, setters...

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
