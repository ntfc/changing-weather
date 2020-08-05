package me.ntfc.changingweather.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class HistoricalWeatherDto {
    @JsonProperty("avg_temp")
    private Double averageTemperature;

    @JsonProperty("avg_pressure")
    private Double averagePressure;

    private List<WeatherDto> history = new LinkedList<>();

    public Double getAverageTemperature() {
        return averageTemperature;
    }

    public void setAverageTemperature(Double averageTemperature) {
        this.averageTemperature = averageTemperature;
    }

    public Double getAveragePressure() {
        return averagePressure;
    }

    public void setAveragePressure(Double averagePressure) {
        this.averagePressure = averagePressure;
    }

    public List<WeatherDto> getHistory() {
        return history;
    }

    public void setHistory(List<WeatherDto> history) {
        this.history = history;
    }
}
