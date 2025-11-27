package com.algomarques.tb.weather_ms.domain.service;

import com.algomarques.tb.weather_ms.domain.dto.WeatherResponse;

import java.time.LocalDate;
import java.util.List;

public interface IWeatherService {

    WeatherResponse checkWeatherStatus(double lat, double lon, List<LocalDate> dates);

}
