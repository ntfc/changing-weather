package me.ntfc.changingweather.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WeatherDto {
    @JsonProperty("temp")
    public Double temperature;

    public Double pressure;

    public boolean umbrella;
}
