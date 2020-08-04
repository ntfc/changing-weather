package me.ntfc.changingweather.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.net.URI;

@ConfigurationProperties(prefix = "openweathermap")
@Configuration
public class OpenWeatherMapProperties {

    private String appId;

    private URI weatherEndpointUri;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public URI getWeatherEndpointUri() {
        return weatherEndpointUri;
    }

    public void setWeatherEndpointUri(URI weatherEndpointUri) {
        this.weatherEndpointUri = weatherEndpointUri;
    }
}
