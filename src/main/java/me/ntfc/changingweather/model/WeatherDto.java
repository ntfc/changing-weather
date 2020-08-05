package me.ntfc.changingweather.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WeatherDto {

    @JsonProperty("temp")
    private Double temperature;

    private Integer pressure;

    private boolean umbrella;

    public WeatherDto() {
    }

    public WeatherDto(Double temperature, Integer pressure, boolean umbrella) {
        this.temperature = temperature;
        this.pressure = pressure;
        this.umbrella = umbrella;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Integer getPressure() {
        return pressure;
    }

    public void setPressure(Integer pressure) {
        this.pressure = pressure;
    }

    public boolean isUmbrella() {
        return umbrella;
    }

    public void setUmbrella(boolean umbrella) {
        this.umbrella = umbrella;
    }
}
