package com.example.weather.model;

import java.util.List;

public class WeatherApiResponse {

    private Coordinates coordinates;
    private MainData main;
    private String name;
    private List<WeatherConditionResponse> weather;

    public Coordinates getCoord() {
        return coordinates;
    }

    public void setCoord(Coordinates coordinates) {
        this.coordinates = coordinates;
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

