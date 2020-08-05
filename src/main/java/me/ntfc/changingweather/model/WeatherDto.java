package me.ntfc.changingweather.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WeatherDto {
    @JsonProperty("temp")
    public Double temperature;

    public Double pressure;

    public boolean umbrella;

    public WeatherDto() {
    }

    public WeatherDto(Double temperature, Double pressure, boolean umbrella) {
        this.temperature = temperature;
        this.pressure = pressure;
        this.umbrella = umbrella;
    }
}
