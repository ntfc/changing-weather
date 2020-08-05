package me.ntfc.changingweather.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.List;
import java.util.Map;

/**
 * Representation of the OpenWeatherMap.org /weather response
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenWeatherMapResponseDto {
    private Double temperature; // metric a.g Celsius

    private Integer pressure;

    private WeatherCondition weatherCondition;

    public OpenWeatherMapResponseDto() {
    }

    public OpenWeatherMapResponseDto(final Double temperature, final Integer pressure, final WeatherCondition weatherCondition) {
        this.temperature = temperature;
        this.pressure = pressure;
        this.weatherCondition = weatherCondition;
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

    public WeatherCondition getWeatherCondition() {
        return weatherCondition;
    }

    public void setWeatherCondition(WeatherCondition weatherCondition) {
        this.weatherCondition = weatherCondition;
    }

    @JsonProperty("main")
    private void unpackNestedFromMain(final Map<String, Object> main) {
        this.temperature = (Double) main.get("temp");
        this.pressure = (Integer) main.get("pressure");
    }

    @JsonProperty("weather")
    private void unpackNestedWeather(final List<Map<String, Object>> weather) {
        weather.stream().findFirst()
                .ifPresent(it -> {
                    String weatherCondition = (String) it.get("main");
                    this.weatherCondition = WeatherCondition.fromDescription(weatherCondition);
                });
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

        public static WeatherCondition fromDescription(final String description) {
            return WeatherCondition.valueOf(description.toUpperCase());
        }

        @JsonValue
        public String getDescription() {
            return description;
        }


    }
}
