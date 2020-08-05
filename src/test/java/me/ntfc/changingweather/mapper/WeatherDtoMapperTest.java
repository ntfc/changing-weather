package me.ntfc.changingweather.mapper;

import me.ntfc.changingweather.model.OpenWeatherMapResponseDto;
import me.ntfc.changingweather.model.WeatherDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static me.ntfc.changingweather.model.OpenWeatherMapResponseDto.WeatherCondition.CLEAR;
import static me.ntfc.changingweather.model.OpenWeatherMapResponseDto.WeatherCondition.DRIZZLE;
import static me.ntfc.changingweather.model.OpenWeatherMapResponseDto.WeatherCondition.RAIN;
import static org.junit.jupiter.api.Assertions.assertEquals;

class WeatherDtoMapperTest {
    private WeatherDtoMapper dtoMapper;

    @BeforeEach
    void setUp() {
        dtoMapper = new WeatherDtoMapper();
    }

    @ParameterizedTest
    @MethodSource("dtosThatNeedUmbrella")
    void testDtosThatNeedsUmbrella(OpenWeatherMapResponseDto openWeatherMapResponseDto, boolean expectedNeedsUmbrella) {
        WeatherDto weatherDto = dtoMapper.fromOpenWeatherMapWeatherDto(openWeatherMapResponseDto);
        assertEquals(openWeatherMapResponseDto.getPressure(), weatherDto.getPressure());
        assertEquals(openWeatherMapResponseDto.getTemperature(), weatherDto.getTemperature());
        assertEquals(expectedNeedsUmbrella, weatherDto.isUmbrella());
    }

    private static Stream<Arguments> dtosThatNeedUmbrella() {
        return Stream.of(
                Arguments.arguments(new OpenWeatherMapResponseDto(54.2, 2412, DRIZZLE), true),
                Arguments.arguments(new OpenWeatherMapResponseDto(54.2, 2412, CLEAR), false),
                Arguments.arguments(new OpenWeatherMapResponseDto(54.2, 2412, RAIN), true)
        );
    }
}