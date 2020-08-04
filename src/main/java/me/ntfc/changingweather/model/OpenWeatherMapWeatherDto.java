package me.ntfc.changingweather.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.List;

/**
 * Representation of the OpenWeatherMap.org /weather response
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenWeatherMapWeatherDto {
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
