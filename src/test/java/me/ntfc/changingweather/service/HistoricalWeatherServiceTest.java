package me.ntfc.changingweather.service;

import me.ntfc.changingweather.model.HistoricalWeatherDto;
import me.ntfc.changingweather.model.WeatherDto;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HistoricalWeatherServiceTest {

    private final HistoricalWeatherService historicalWeatherService = new HistoricalWeatherService();

    @Test
    void calculationsAreCorrect() {
        List<String> city = Collections.singletonList("London");

        WeatherDto weather1 = new WeatherDto(6.0, 1000, true);
        WeatherDto weather2 = new WeatherDto(8.0, 1500, false);
        historicalWeatherService.collect(city, weather1);
        historicalWeatherService.collect(city, weather2);

        Optional<HistoricalWeatherDto> maybeCityHistory = historicalWeatherService.getHistory(city);

        assertTrue(maybeCityHistory.isPresent());
        HistoricalWeatherDto history = maybeCityHistory.get();
        assertEquals(1250.0, history.getAveragePressure());
        assertEquals(7, history.getAverageTemperature());
        assertEquals(2, history.getHistory().size());
        assertEquals(weather2, history.getHistory().get(0));
        assertEquals(weather1, history.getHistory().get(1));
    }

    // the average is calculated using the last 5 WeatherDto objects e.g
    // avg(weather6, weather5, weather4, weather3, weather2)
    @Test
    void onlyLastFiveTemperaturesAreUsedInCalculations() {
        List<String> city = Collections.singletonList("London");

        WeatherDto weather1 = new WeatherDto(6.0, 1000, true);
        WeatherDto weather2 = new WeatherDto(8.0, 1500, false);
        WeatherDto weather3 = new WeatherDto(1.0, 1500, true);
        WeatherDto weather4 = new WeatherDto(-5.0, 1500, true);
        WeatherDto weather5 = new WeatherDto(15.0, 1250, false);
        WeatherDto weather6 = new WeatherDto(11.5, 1100, false);

        historicalWeatherService.collect(city, weather1);
        historicalWeatherService.collect(city, weather2);
        historicalWeatherService.collect(city, weather3);
        historicalWeatherService.collect(city, weather4);
        historicalWeatherService.collect(city, weather5);
        historicalWeatherService.collect(city, weather6);

        Optional<HistoricalWeatherDto> maybeCityHistory = historicalWeatherService.getHistory(city);
        assertTrue(maybeCityHistory.isPresent());
        HistoricalWeatherDto history = maybeCityHistory.get();
        assertEquals(1370, history.getAveragePressure());
        assertEquals(6.1, history.getAverageTemperature());
        assertEquals(6, history.getHistory().size());
    }
}