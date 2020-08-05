package me.ntfc.changingweather.mapper;

import me.ntfc.changingweather.model.OpenWeatherMapWeatherDto;
import me.ntfc.changingweather.model.OpenWeatherMapWeatherDto.WeatherConditionDto;
import me.ntfc.changingweather.model.WeatherDto;
import org.springframework.stereotype.Component;

import java.util.List;

import static me.ntfc.changingweather.model.OpenWeatherMapWeatherDto.WeatherCondition.DRIZZLE;
import static me.ntfc.changingweather.model.OpenWeatherMapWeatherDto.WeatherCondition.RAIN;
import static me.ntfc.changingweather.model.OpenWeatherMapWeatherDto.WeatherCondition.THUNDERSTORM;

@Component
public class WeatherDtoMapper {

    public WeatherDto fromOpenWeatherMapWeatherDto(final OpenWeatherMapWeatherDto openWeatherMapWeatherDto) {
        return new WeatherDto(
                openWeatherMapWeatherDto.main.getTemperature(),
                openWeatherMapWeatherDto.main.getPressure(),
                needsUmbrella(openWeatherMapWeatherDto.weather)
        );
    }

    /**
     * Checks if a given WeatherConditionDto from OpenWeatherMap API requires an umbrella or not.
     *
     * Methods receives a {@code List<WeatherConditionDto>} since the OpenWeatherMap API can also more than one weather
     * condition, even though the first one is the primary:
     *
     * > NOTE: It is possible to meet more than one weather condition for a requested location. The first weather
     * > condition in API respond is primary.
     * >
     * > Source: https://openweathermap.org/weather-conditions#Weather-Condition-Codes-2
     */
    private boolean needsUmbrella(List<WeatherConditionDto> weatherConditions) {
        return weatherConditions.stream()
                .findFirst()
                .map(WeatherConditionDto::getCondition)
                .map(it -> it == DRIZZLE || it == RAIN || it == THUNDERSTORM)
                .orElse(false);
    }
}
