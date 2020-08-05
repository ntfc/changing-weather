package me.ntfc.changingweather.service;

import me.ntfc.changingweather.model.HistoricalWeatherDto;
import me.ntfc.changingweather.model.WeatherDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class HistoricalWeatherService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HistoricalWeatherService.class);

    private final Map<String, HistoricalWeatherDto> historicalData;

    public HistoricalWeatherService() {
        this.historicalData = new ConcurrentHashMap<>();
    }

    public void collect(final List<String> cityInfo, final WeatherDto weatherForCity) {
        LOGGER.info("Collecting data for {} ", cityInfo);

        String city = String.join(",", cityInfo);

        historicalData.computeIfPresent(city, (key, history) -> {
            history.getHistory().addFirst(weatherForCity); // head insertion
            double averagePressure = history.getHistory().stream()
                    .limit(5)
                    .map(WeatherDto::getPressure)
                    .mapToDouble(Double::valueOf)
                    .average()
                    .orElse(0.0);
            double averageTemperature = history.getHistory().stream()
                    .limit(5)
                    .map(WeatherDto::getTemperature)
                    .mapToDouble(Double::valueOf)
                    .average()
                    .orElse(0.0);

            history.setAveragePressure(averagePressure);
            history.setAverageTemperature(averageTemperature);
            return history;
        });

        historicalData.computeIfAbsent(city, key -> {
            HistoricalWeatherDto history = new HistoricalWeatherDto();
            history.getHistory().addFirst(weatherForCity); // head insertion
            history.setAveragePressure(weatherForCity.getPressure().doubleValue());
            history.setAverageTemperature(weatherForCity.getTemperature());
            return history;
        });

        LOGGER.info("Done for {}", cityInfo);
    }

    public Optional<HistoricalWeatherDto> getHistory(final List<String> cityInfo) {
        return Optional.ofNullable(historicalData.get(String.join(",", cityInfo)));
    }
}
