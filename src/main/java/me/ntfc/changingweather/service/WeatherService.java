package me.ntfc.changingweather.service;

import me.ntfc.changingweather.client.OpenWeatherMapClient;
import me.ntfc.changingweather.mapper.WeatherDtoMapper;
import me.ntfc.changingweather.model.OpenWeatherMapResponseDto;
import me.ntfc.changingweather.model.WeatherDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class WeatherService {

    private final OpenWeatherMapClient weatherClient;

    private final WeatherDtoMapper weatherDtoMapper;

    private final HistoricalWeatherService historicalWeatherService;

    public WeatherService(final OpenWeatherMapClient weatherClient, final WeatherDtoMapper weatherDtoMapper, final HistoricalWeatherService historicalWeatherService) {
        this.weatherClient = weatherClient;
        this.weatherDtoMapper = weatherDtoMapper;
        this.historicalWeatherService = historicalWeatherService;
    }

    public Optional<WeatherDto> getWeatherForCity(List<String> cityInfo) {
        OpenWeatherMapResponseDto weatherForCity = weatherClient.getWeatherForCity(cityInfo);

        WeatherDto responseDto = weatherDtoMapper.fromOpenWeatherMapWeatherDto(weatherForCity);

        // async collect historical data
        CompletableFuture.runAsync(() -> historicalWeatherService.collect(cityInfo, responseDto));

        return Optional.of(responseDto);
    }
}
