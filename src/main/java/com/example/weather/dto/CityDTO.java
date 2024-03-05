package com.example.weather.dto;

import java.util.ArrayList;
import java.util.List;

public class CityDTO {
    private Long id;
    private String name;
    private List<WeatherDataDTO> weatherDataList = new ArrayList<>();

    // Constructors, getters, setters...

    public CityDTO() {
    }

    public CityDTO(Long id, String name, List<WeatherDataDTO> weatherDataList) {
        this.id = id;
        this.name = name;
        this.weatherDataList = weatherDataList;
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<WeatherDataDTO> getWeatherDataList() {
        return weatherDataList;
    }

    public void setWeatherDataList(List<WeatherDataDTO> weatherDataList) {
        this.weatherDataList = weatherDataList;
    }
}
