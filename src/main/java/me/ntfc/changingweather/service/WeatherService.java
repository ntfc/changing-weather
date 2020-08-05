package me.ntfc.changingweather.service;

import me.ntfc.changingweather.client.OpenWeatherMapClient;
import me.ntfc.changingweather.mapper.WeatherDtoMapper;
import me.ntfc.changingweather.model.OpenWeatherMapResponseDto;
import me.ntfc.changingweather.model.WeatherDto;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {

    private final OpenWeatherMapClient weatherClient;

    private final WeatherDtoMapper weatherDtoMapper;

    public WeatherService(final OpenWeatherMapClient weatherClient, final WeatherDtoMapper weatherDtoMapper) {
        this.weatherClient = weatherClient;
        this.weatherDtoMapper = weatherDtoMapper;
    }

    public WeatherDto getWeatherForCity(String city) {
        OpenWeatherMapResponseDto weatherForCity = weatherClient.getWeatherForCity(city);

        return weatherDtoMapper.fromOpenWeatherMapWeatherDto(weatherForCity);
    }
}
