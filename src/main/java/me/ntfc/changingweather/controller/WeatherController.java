package me.ntfc.changingweather.controller;

import me.ntfc.changingweather.model.HistoricalWeatherDto;
import me.ntfc.changingweather.model.WeatherDto;
import me.ntfc.changingweather.service.HistoricalWeatherService;
import me.ntfc.changingweather.service.WeatherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    private final WeatherService weatherService;
    private final HistoricalWeatherService historicalWeatherService;

    public WeatherController(final WeatherService weatherService, final HistoricalWeatherService historicalWeatherService) {
        this.weatherService = weatherService;
        this.historicalWeatherService = historicalWeatherService;
    }

    @GetMapping("/current")
    public ResponseEntity<WeatherDto> getWeatherForCity(@RequestParam("location") List<String> cityInfo) {
        return ResponseEntity.of(weatherService.getWeatherForCity(cityInfo));
    }

    @GetMapping("/history")
    public ResponseEntity<HistoricalWeatherDto> getHistoricalWeatherForCity(@RequestParam("location") List<String> cityInfo) {
        return ResponseEntity.of(historicalWeatherService.getHistory(cityInfo));
    }
}
