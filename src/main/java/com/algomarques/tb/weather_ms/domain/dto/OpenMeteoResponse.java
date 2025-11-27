package com.algomarques.tb.weather_ms.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record OpenMeteoResponse(double latitude,
                                double longitude,
                                @JsonProperty("generationtime_ms") double generationTimeMs,
                                @JsonProperty("utc_offset_seconds") int utcOffsetSeconds,
                                String timezone,
                                @JsonProperty("timezone_abbreviation") String timezoneAbbreviation,
                                double elevation,
                                @JsonProperty("daily_units") DailyUnits dailyUnits,
                                Daily daily) {

    public record DailyUnits(
        String time,
        @JsonProperty("temperature_2m_min") String temperature2mMin,
        @JsonProperty("temperature_2m_max") String temperature2mMax,
        @JsonProperty("weather_code") String weatherCode
    ) {}

    public record Daily(
        List<String> time,
        @JsonProperty("temperature_2m_min") List<Double> temperature2mMin,
        @JsonProperty("temperature_2m_max") List<Double> temperature2mMax,
        @JsonProperty("weather_code") List<Integer> weatherCode
    ) {}
}
