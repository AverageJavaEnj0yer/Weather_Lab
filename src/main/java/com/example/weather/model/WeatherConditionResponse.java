package com.example.weather.model;

import lombok.Data;

@Data
public class WeatherConditionResponse {
    private String main;
    private String description;
    private String icon;
}
