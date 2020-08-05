package me.ntfc.changingweather.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Files;
import java.nio.file.Path;

import static me.ntfc.changingweather.model.OpenWeatherMapResponseDto.WeatherCondition.DRIZZLE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class OpenWeatherMapResponseDtoTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void parseLondonWeatherUsingSpringObjectMapper() throws Exception {
        String json = Files.readString(Path.of(getClass().getResource("/london_weather.json").toURI()));
        OpenWeatherMapResponseDto dto = objectMapper.readValue(json, OpenWeatherMapResponseDto.class);

        assertNotNull(dto);
        assertEquals(DRIZZLE, dto.getWeatherCondition());

        assertEquals(280.32, dto.getTemperature());
        assertEquals(1012, dto.getPressure());
    }
}