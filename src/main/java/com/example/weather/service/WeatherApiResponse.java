package com.example.weather.service;

import com.example.weather.entity.Coord;
import com.fasterxml.jackson.annotation.JsonProperty;

public class WeatherApiResponse {
    private Coord coord;
    private String name;

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
