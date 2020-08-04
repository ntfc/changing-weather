package me.ntfc.changingweather.client;

import me.ntfc.changingweather.config.OpenWeatherMapProperties;
import me.ntfc.changingweather.model.OpenWeatherMapWeatherDto;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
public class OpenWeatherMapClient {

    private final RestTemplate restTemplate;

    private final OpenWeatherMapProperties openWeatherMapProperties;

    public OpenWeatherMapClient(final RestTemplate restTemplate, final OpenWeatherMapProperties openWeatherMapProperties) {
        this.restTemplate = restTemplate;
        this.openWeatherMapProperties = openWeatherMapProperties;
    }

    public OpenWeatherMapWeatherDto getWeatherForCity(final String city) {
        URI uri = UriComponentsBuilder.fromUri(openWeatherMapProperties.getWeatherEndpointUri())
                .queryParam("appid", openWeatherMapProperties.getAppId())
                .queryParam("q", city)
                .build()
                .toUri();

        return restTemplate
                .getForEntity(uri, OpenWeatherMapWeatherDto.class)
                .getBody();
    }
}
