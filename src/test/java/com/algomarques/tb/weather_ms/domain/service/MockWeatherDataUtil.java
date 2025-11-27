package com.algomarques.tb.weather_ms.domain.service;

import com.algomarques.tb.weather_ms.domain.dto.OpenMeteoResponse;

import java.time.LocalDate;
import java.util.List;

public class MockWeatherDataUtil {
    public static OpenMeteoResponse mockOpenMeteoResponse() {
        List<String> times = List.of(
            LocalDate.of(2023, 1, 1).toString(),
            LocalDate.of(2023, 1, 2).toString(),
            LocalDate.of(2023, 1, 3).toString()
        );

        OpenMeteoResponse.Daily daily = new OpenMeteoResponse.Daily(
            times,
            List.of(10.0, 11.0, 12.0), // temperature_2m_min
            List.of(15.0, 16.0, 17.0), // temperature_2m_max
            List.of(1, 2, 3) // weather_code
        );

        OpenMeteoResponse.DailyUnits dailyUnits = new OpenMeteoResponse.DailyUnits(
            "time",
            "C",
            "C",
            "code"
        );

        return new OpenMeteoResponse(
            1.0,
            2.0,
            1.0,
            0,
            "UTC",
            "UTC",
            100.0,
            dailyUnits,
            daily
        );
    }
}

