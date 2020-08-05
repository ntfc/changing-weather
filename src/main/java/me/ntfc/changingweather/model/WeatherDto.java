package me.ntfc.changingweather.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WeatherDto that = (WeatherDto) o;
        return umbrella == that.umbrella &&
                Objects.equals(temperature, that.temperature) &&
                Objects.equals(pressure, that.pressure);
    }

    @Override
    public int hashCode() {
        return Objects.hash(temperature, pressure, umbrella);
    }
}
