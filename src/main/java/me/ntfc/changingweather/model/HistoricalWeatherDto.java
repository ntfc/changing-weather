package me.ntfc.changingweather.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.LinkedList;

public class HistoricalWeatherDto {
    @JsonProperty("avg_temp")
    private Double averageTemperature;

    @JsonProperty("avg_pressure")
    private Double averagePressure;

    private LinkedList<WeatherDto> history = new LinkedList<>();

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

    public LinkedList<WeatherDto> getHistory() {
        return history;
    }

    public void setHistory(LinkedList<WeatherDto> history) {
        this.history = history;
    }
}
