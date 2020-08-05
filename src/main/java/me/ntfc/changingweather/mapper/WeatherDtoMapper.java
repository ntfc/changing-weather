package me.ntfc.changingweather.mapper;

import me.ntfc.changingweather.model.OpenWeatherMapResponseDto;
import me.ntfc.changingweather.model.OpenWeatherMapResponseDto.WeatherCondition;
import me.ntfc.changingweather.model.WeatherDto;
import org.springframework.stereotype.Component;

import static me.ntfc.changingweather.model.OpenWeatherMapResponseDto.WeatherCondition.DRIZZLE;
import static me.ntfc.changingweather.model.OpenWeatherMapResponseDto.WeatherCondition.RAIN;
import static me.ntfc.changingweather.model.OpenWeatherMapResponseDto.WeatherCondition.THUNDERSTORM;

@Component
public class WeatherDtoMapper {

    public WeatherDto fromOpenWeatherMapWeatherDto(final OpenWeatherMapResponseDto openWeatherMapResponseDto) {
        return new WeatherDto(
                openWeatherMapResponseDto.getTemperature(),
                openWeatherMapResponseDto.getPressure(),
                needsUmbrella(openWeatherMapResponseDto.getWeatherCondition())
        );
    }

    /**
     * Checks if a given WeatherCondition from OpenWeatherMap API requires an umbrella or not.
     */
    private boolean needsUmbrella(final WeatherCondition weatherConditions) {
        return weatherConditions == DRIZZLE || weatherConditions == RAIN || weatherConditions == THUNDERSTORM;
    }
}
