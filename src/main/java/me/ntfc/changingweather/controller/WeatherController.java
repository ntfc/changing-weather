package me.ntfc.changingweather.controller;

import me.ntfc.changingweather.model.WeatherDto;
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

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/current")
    public ResponseEntity<WeatherDto> getWeatherForCity(@RequestParam(value = "location") List<String> cityInfo) {
        return ResponseEntity.ok(weatherService.getWeatherForCity(cityInfo));
    }
}
