package com.example.weather.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MainData {
    @JsonProperty("temp")
    private double temperature;

    @JsonProperty("humidity")
    private double humidity;

    public double getTemp() {
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
}
