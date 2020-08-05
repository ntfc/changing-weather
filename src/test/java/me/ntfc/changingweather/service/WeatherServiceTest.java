package me.ntfc.changingweather.service;

import me.ntfc.changingweather.client.OpenWeatherMapClient;
import me.ntfc.changingweather.mapper.WeatherDtoMapper;
import me.ntfc.changingweather.model.OpenWeatherMapResponseDto;
import me.ntfc.changingweather.model.WeatherDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static me.ntfc.changingweather.model.OpenWeatherMapResponseDto.WeatherCondition.CLEAR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WeatherServiceTest {

    @Mock
    private OpenWeatherMapClient openWeatherMapClient;

    @Spy
    private WeatherDtoMapper weatherDtoMapper = new WeatherDtoMapper();

    @InjectMocks
    private WeatherService weatherService;

    @Mock
    private HistoricalWeatherService historicalWeatherService;

    @Test
    void testHistoricalServiceIsInvoked() {
        List<String> cityInfo = Collections.singletonList("CityThatDoesExistYet");
        OpenWeatherMapResponseDto openWeatherResponseDto = new OpenWeatherMapResponseDto(32.4, 1200, CLEAR);

        when(openWeatherMapClient.getWeatherForCity(cityInfo)).thenReturn(openWeatherResponseDto);

        Optional<WeatherDto> maybeWeatherDto = weatherService.getWeatherForCity(cityInfo);

        assertTrue(maybeWeatherDto.isPresent());
        assertEquals(1200, maybeWeatherDto.get().getPressure());
        assertEquals(32.4, maybeWeatherDto.get().getTemperature());
        assertFalse(maybeWeatherDto.get().isUmbrella());


        verify(openWeatherMapClient).getWeatherForCity(eq(cityInfo));
        verify(weatherDtoMapper).fromOpenWeatherMapWeatherDto(eq(openWeatherResponseDto));
        verify(historicalWeatherService).collect(eq(cityInfo), eq(maybeWeatherDto.get()));
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(openWeatherMapClient, weatherDtoMapper, weatherDtoMapper, historicalWeatherService);
    }
}