package com.algomarques.tb.weather_ms.domain.dto;

import lombok.Getter;

@Getter
public enum WeatherType {

    CLEAR(0, 0, "Clear sky"),
    CLOUDY(1, 3, "Cloudy"),
    SNOW(30, 39, "Snow"),
    FOG(40, 49, "Fog"),
    LIGHT_RAIN(50, 59, "Light rain"),
    RAIN(60, 69, "Rain"),
    HEAVY_RAIN(70, 99, "Heavy Rain"),
    UNKNOWN(Integer.MIN_VALUE, Integer.MAX_VALUE, "Unknown");

    private final int minCode;
    private final int maxCode;
    private final String weatherStatus;

    WeatherType(int minCode, int maxCode, String weatherStatus) {
        this.minCode = minCode;
        this.maxCode = maxCode;
        this.weatherStatus = weatherStatus;
    }

    public static String fromCode(int code) {
        for (WeatherType type : values()) {
            if (code >= type.minCode && code <= type.maxCode) {
                return type.getWeatherStatus();
            }
        }
        return UNKNOWN.getWeatherStatus();
    }

}
