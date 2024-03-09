// В файле WeatherApiResponse.java
package com.example.weather.service;

import com.example.weather.model.Coord;
import com.example.weather.model.MainData;
import com.example.weather.model.WeatherConditionResponse;

import java.util.List;

public class WeatherApiResponse {
    private Coord coord;
    private MainData main;
    private String name;
    private List<WeatherConditionResponse> weather; // Добавляем поле для списка погодных условий

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public MainData getMain() {
        return main;
    }

    public void setMain(MainData main) {
        this.main = main;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<WeatherConditionResponse> getWeather() {
        return weather;
    }

    public void setWeather(List<WeatherConditionResponse> weather) {
        this.weather = weather;
    }
}
