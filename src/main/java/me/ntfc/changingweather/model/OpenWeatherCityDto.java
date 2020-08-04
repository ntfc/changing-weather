package me.ntfc.changingweather.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Representation of the OpenWeatherMap.org /weather response
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenWeatherCityDto {
    public List<WeatherConditionDto> weather;

    public MainDto main;

    public static final class MainDto {
        @JsonProperty("temp")
        public Double temperature;

        public Double pressure;
    }

    public static final class WeatherConditionDto {
        @JsonProperty("main")
        public WeatherCondition condition;
    }

    /**
     * Values taken from https://openweathermap.org/weather-conditions#Weather-Condition-Codes-2
     */
    public enum WeatherCondition {
        THUNDERSTORM,
        DRIZZLE,
        RAIN,
        SNOW,
        ATMOSPHERE,
        CLEAR,
        CLOUDS,
        ;
    }
}
