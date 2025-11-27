package com.algomarques.tb.weather_ms.domain.service;

import com.algomarques.tb.weather_ms.domain.dto.DailyWeather;
import com.algomarques.tb.weather_ms.domain.dto.WeatherResponse;
import com.algomarques.tb.weather_ms.domain.repository.OpenMeteoClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;

class WeatherServiceTest {

    private OpenMeteoClient openMeteoClient;
    private WeatherService weatherService;

    @BeforeEach
    void setUp() {
        openMeteoClient = Mockito.mock(OpenMeteoClient.class);
        weatherService = new WeatherService(openMeteoClient);
    }

    @Test
    void testCheckWeatherStatus_withValidDates_returnsWeatherResponse() {
        LocalDate start = LocalDate.of(2023, 1, 1);
        LocalDate end = LocalDate.of(2023, 1, 3);
        List<LocalDate> dates = List.of(start, end);

        Mockito.when(openMeteoClient.getOpenMeteoData(anyDouble(), anyDouble(), any(LocalDate.class), any(LocalDate.class), anyString()))
                .thenReturn(MockWeatherDataUtil.mockOpenMeteoResponse());

        WeatherResponse response = weatherService.checkWeatherStatus(1.0, 2.0, dates);
        assertNotNull(response);
        assertEquals(3, response.getDailyWeather().size());
        for (DailyWeather dw : response.getDailyWeather()) {
            assertNotNull(dw.getDate());
        }
    }

    @Test
    void testCheckWeatherStatus_withInvalidDates_throwsException() {
        LocalDate start = LocalDate.of(2023, 1, 3);
        LocalDate end = LocalDate.of(2023, 1, 1);
        List<LocalDate> dates = List.of(start, end);
        assertThrows(IllegalArgumentException.class, () -> weatherService.checkWeatherStatus(1.0, 2.0, dates));
    }

    @Test
    void testCheckWeatherStatus_withNullDates_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> weatherService.checkWeatherStatus(1.0, 2.0, null));
    }

    @Test
    void testCheckWeatherStatus_withSingleDate_throwsException() {
        List<LocalDate> dates = List.of(LocalDate.of(2023, 1, 1));
        assertThrows(IllegalArgumentException.class, () -> weatherService.checkWeatherStatus(1.0, 2.0, dates));
    }
}

