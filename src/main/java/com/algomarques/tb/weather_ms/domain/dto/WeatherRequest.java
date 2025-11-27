package com.algomarques.tb.weather_ms.domain.dto;

import java.time.LocalDateTime;

public record WeatherRequest(Long latitude, Long longitude, LocalDateTime date) {
}
