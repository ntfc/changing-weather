package me.ntfc.changingweather.client;

import me.ntfc.changingweather.config.OpenWeatherMapProperties;
import me.ntfc.changingweather.model.OpenWeatherMapResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Component
public class OpenWeatherMapClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(OpenWeatherMapClient.class);

    private final RestTemplate restTemplate;

    private final OpenWeatherMapProperties openWeatherMapProperties;

    public OpenWeatherMapClient(final RestTemplate restTemplate, final OpenWeatherMapProperties openWeatherMapProperties) {
        this.restTemplate = restTemplate;
        this.openWeatherMapProperties = openWeatherMapProperties;
    }

    public OpenWeatherMapResponseDto getWeatherForCity(final List<String> cityInfo) {
        URI uri = UriComponentsBuilder.fromUri(openWeatherMapProperties.getWeatherEndpointUri())
                .queryParam("q", String.join(",", cityInfo))
                .queryParam("units", "metric")
                .queryParam("appid", openWeatherMapProperties.getAppId())
                .build()
                .toUri();

        return restTemplate
                .getForEntity(uri, OpenWeatherMapResponseDto.class)
                .getBody();
    }
}
