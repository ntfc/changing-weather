package me.ntfc.changingweather.mapper;

import me.ntfc.changingweather.model.OpenWeatherMapWeatherDto;
import me.ntfc.changingweather.model.WeatherDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static me.ntfc.changingweather.model.OpenWeatherMapWeatherDto.WeatherCondition.CLEAR;
import static me.ntfc.changingweather.model.OpenWeatherMapWeatherDto.WeatherCondition.DRIZZLE;
import static me.ntfc.changingweather.model.OpenWeatherMapWeatherDto.WeatherCondition.RAIN;
import static org.junit.jupiter.api.Assertions.assertEquals;

class WeatherDtoMapperTest {
    private WeatherDtoMapper dtoMapper;

    @BeforeEach
    void setUp() {
        dtoMapper = new WeatherDtoMapper();
    }

    @ParameterizedTest
    @MethodSource("dtosThatNeedUmbrella")
    void testDtosThatNeedsUmbrella(OpenWeatherMapWeatherDto openWeatherMapWeatherDto, boolean expectedNeedsUmbrella) {
        WeatherDto weatherDto = dtoMapper.fromOpenWeatherMapWeatherDto(openWeatherMapWeatherDto);
        assertEquals(openWeatherMapWeatherDto.main.getPressure(), weatherDto.pressure);
        assertEquals(openWeatherMapWeatherDto.main.getTemperature(), weatherDto.temperature);
        assertEquals(expectedNeedsUmbrella, weatherDto.umbrella);
    }

    private static Stream<Arguments> dtosThatNeedUmbrella() {
        return Stream.of(
                Arguments.arguments(new OpenWeatherMapWeatherDto(54.2, 2412.0, DRIZZLE), true),
                Arguments.arguments(new OpenWeatherMapWeatherDto(54.2, 2412.0, CLEAR), false),
                Arguments.arguments(new OpenWeatherMapWeatherDto(54.2, 2412.0, RAIN), true)
        );
    }
}