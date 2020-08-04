package me.ntfc.changingweather.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Files;
import java.nio.file.Path;

import static me.ntfc.changingweather.model.OpenWeatherMapWeatherDto.WeatherCondition.DRIZZLE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class OpenWeatherMapWeatherDtoTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void parseLondonWeatherUsingSpringObjectMapper() throws Exception {
        String json = Files.readString(Path.of(getClass().getResource("/london_weather.json").toURI()));
        OpenWeatherMapWeatherDto dto = objectMapper.readValue(json, OpenWeatherMapWeatherDto.class);

        assertNotNull(dto);
        assertEquals(1, dto.weather.size());
        assertEquals(DRIZZLE, dto.weather.get(0).condition);

        assertEquals(280.32, dto.main.temperature);
        assertEquals(1012, dto.main.pressure);
    }
}