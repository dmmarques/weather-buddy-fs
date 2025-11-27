package com.algomarques.tb.weather_ms.domain.service;

import com.algomarques.tb.weather_ms.domain.dto.OpenMeteoResponse;
import com.algomarques.tb.weather_ms.domain.dto.WeatherResponse;
import com.algomarques.tb.weather_ms.domain.repository.OpenMeteoHistoricalClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class WeatherHistoricalServiceTest {

    @Mock
    private OpenMeteoHistoricalClient openMeteoClient;

    @InjectMocks
    private WeatherHistoricalService service;

    private static final double LAT = 10.0;
    private static final double LON = 20.0;
    private static final LocalDate DATE = LocalDate.of(2023, 5, 16);

    private OpenMeteoResponse createMockResponse(LocalDate date, Integer weatherCode) {
        OpenMeteoResponse.Daily daily = new OpenMeteoResponse.Daily(
            List.of(date.toString()),
            List.of(10.0),
            List.of(15.0),
            List.of(weatherCode)
        );

        return new OpenMeteoResponse(
            LAT,
            LON,
            0.124,
            0,
            "UTC",
            "UTC",
            111.5,
            new OpenMeteoResponse.DailyUnits("time", "C", "C", "weather_code"),
            daily
        );
    }

    @Test
    @DisplayName("should return weather response with valid daily weather")
    void test1() {
        OpenMeteoResponse mockResponse = createMockResponse(DATE, 55);

        Mockito.when(openMeteoClient.getOpenMeteoArchiveData(Mockito.eq(LAT), Mockito.eq(LON), Mockito.eq(DATE), Mockito.eq(DATE), Mockito.anyString()))
               .thenReturn(mockResponse);

        WeatherResponse result = service.checkWeatherStatus(LAT, LON, List.of(DATE));

        assertAll(() -> {
            assertThat(result.getLatitude()).isEqualTo(LAT);
            assertThat(result.getLongitude()).isEqualTo(LON);
            assertThat(result.getDailyWeather()).hasSize(1);
            assertThat(result.getDailyWeather().getFirst().getWeatherCode()).isEqualTo(55);
            assertThat(result.getDailyWeather().getFirst().getDate()).isEqualTo(DATE);
            assertThat(result.getDailyWeather().getFirst().getWeatherStatus()).isEqualTo("Light rain");
        });
    }

    @Test
    @DisplayName("should return empty weather response when provider returns no data")
    void test2() {
        // Build a response with empty daily lists to simulate no data
        OpenMeteoResponse.Daily emptyDaily = new OpenMeteoResponse.Daily(List.of(), List.of(), List.of(), List.of());
        OpenMeteoResponse emptyResponse = new OpenMeteoResponse(
            LAT, LON, 0.0, 0, "UTC", "UTC", 0.0,
            new OpenMeteoResponse.DailyUnits("time", "C", "C", "code"),
            emptyDaily
        );

        Mockito.when(openMeteoClient.getOpenMeteoArchiveData(Mockito.eq(LAT), Mockito.eq(LON), Mockito.eq(DATE), Mockito.eq(DATE), Mockito.anyString()))
               .thenReturn(emptyResponse);

        WeatherResponse result = service.checkWeatherStatus(LAT, LON, List.of(DATE));

        assertAll(() -> {
            assertThat(result.getLatitude()).isEqualTo(LAT);
            assertThat(result.getLongitude()).isEqualTo(LON);
            assertThat(result.getDailyWeather()).isEmpty();
        });
    }

    @Test
    @DisplayName("should throw OpenMeteoException when client fails")
    void test3() {
        Mockito.when(openMeteoClient.getOpenMeteoArchiveData(Mockito.eq(LAT), Mockito.eq(LON), Mockito.eq(DATE), Mockito.eq(DATE), Mockito.anyString()))
               .thenThrow(new RuntimeException("Random Error"));

        assertThrows(com.algomarques.tb.weather_ms.common.exception.OpenMeteoException.class,
            () -> service.checkWeatherStatus(LAT, LON, List.of(DATE)));
    }

}