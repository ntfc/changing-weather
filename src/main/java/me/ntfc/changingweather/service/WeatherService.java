package me.ntfc.changingweather.service;

import me.ntfc.changingweather.client.OpenWeatherMapClient;
import me.ntfc.changingweather.model.OpenWeatherMapWeatherDto;
import me.ntfc.changingweather.model.WeatherDto;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {

    private final OpenWeatherMapClient weatherClient;

    public WeatherService(OpenWeatherMapClient weatherClient) {
        this.weatherClient = weatherClient;
    }

    public WeatherDto getWeatherForCity(String city) {
        OpenWeatherMapWeatherDto weatherForCity = weatherClient.getWeatherForCity(city);

        WeatherDto weatherDto = new WeatherDto();

        weatherDto.pressure = weatherForCity.main.pressure;
        weatherDto.temperature = weatherForCity.main.temperature;
        weatherDto.umbrella = true;
        return weatherDto;
    }
}
