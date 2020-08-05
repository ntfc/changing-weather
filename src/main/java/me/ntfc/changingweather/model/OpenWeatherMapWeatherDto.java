package me.ntfc.changingweather.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Collections;
import java.util.List;

/**
 * Representation of the OpenWeatherMap.org /weather response
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenWeatherMapWeatherDto {
    public List<WeatherConditionDto> weather;

    public MainDto main;

    public OpenWeatherMapWeatherDto() {
    }

    public OpenWeatherMapWeatherDto(final Double temperature, final Double pressure, final WeatherCondition weatherCondition) {
        this.weather = Collections.singletonList(new WeatherConditionDto(weatherCondition));
        this.main = new MainDto(temperature, pressure);
    }

    public static final class MainDto {
        @JsonProperty("temp")
        private Double temperature;

        private Double pressure;

        public MainDto() {
        }

        public MainDto(Double temperature, Double pressure) {
            this.temperature = temperature;
            this.pressure = pressure;
        }

        public Double getTemperature() {
            return temperature;
        }

        public void setTemperature(Double temperature) {
            this.temperature = temperature;
        }

        public Double getPressure() {
            return pressure;
        }

        public void setPressure(Double pressure) {
            this.pressure = pressure;
        }
    }

    public static final class WeatherConditionDto {
        @JsonProperty("main")
        private WeatherCondition condition;

        public WeatherConditionDto() {
        }

        public WeatherConditionDto(WeatherCondition condition) {
            this.condition = condition;
        }

        public WeatherCondition getCondition() {
            return condition;
        }

        public void setCondition(WeatherCondition condition) {
            this.condition = condition;
        }
    }

    /**
     * Values taken from https://openweathermap.org/weather-conditions#Weather-Condition-Codes-2
     */
    public enum WeatherCondition {
        THUNDERSTORM("Thunderstorm"),
        DRIZZLE("Drizzle"),
        RAIN("Rain"),
        SNOW("Snow"),
        ATMOSPHERE("Atmosphere"),
        CLEAR("Clear"),
        CLOUDS("Clouds"),
        ;

        private final String description;

        WeatherCondition(String description) {
            this.description = description;
        }

        @JsonValue
        public String getDescription() {
            return description;
        }


    }
}
