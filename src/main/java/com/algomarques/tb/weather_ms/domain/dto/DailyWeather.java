package com.algomarques.tb.weather_ms.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class DailyWeather {
    private LocalDate date;
    private Integer weatherCode;
    private Double minTemperature;
    private Double maxTemperature;

    @JsonProperty("weatherStatus")
    public String getWeatherStatus() {
        return WeatherType.fromCode(weatherCode);
    }

}
