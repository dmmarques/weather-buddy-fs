package com.algomarques.tb.weather_ms.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder(setterPrefix = "with", builderMethodName = "init")
public class WeatherResponse {
    private final double latitude;
    private final double longitude;
    private final List<DailyWeather> dailyWeather;
}
