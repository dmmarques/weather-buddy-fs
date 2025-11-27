package com.algomarques.tb.weather_ms.domain.service;

import com.algomarques.tb.weather_ms.common.exception.OpenMeteoException;
import com.algomarques.tb.weather_ms.domain.dto.DailyWeather;
import com.algomarques.tb.weather_ms.domain.dto.OpenMeteoResponse;
import com.algomarques.tb.weather_ms.domain.dto.WeatherResponse;
import com.algomarques.tb.weather_ms.domain.repository.OpenMeteoHistoricalClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component("historicalService")
@RequiredArgsConstructor
public class WeatherHistoricalService implements IWeatherService {

    private final OpenMeteoHistoricalClient openMeteoClient;

    public WeatherResponse checkWeatherStatus(double lat, double lon, List<LocalDate> dates) {
        List<DailyWeather> dailyWeatherList = dates.stream()
                                                   .map(date -> fetchWeatherForDate(lat, lon, date))
                                                   .filter(Optional::isPresent)
                                                   .map(Optional::get)
                                                   .toList();

        return WeatherResponse.init()
                              .withLatitude(lat)
                              .withLongitude(lon)
                              .withDailyWeather(dailyWeatherList)
                              .build();
    }

    /*
    For now, we will do a single request per day of travel
    As customers increase this will need to be changed so that a single request is done for several requests
        If they are in consecutive days.
    For now, this behaviour is enough.
    */
    private Optional<DailyWeather> fetchWeatherForDate(double lat, double lon, LocalDate date) {
        OpenMeteoResponse response;

        try {
            log.info("Invoking OpenMeteo for weather in {}", date);
            response = openMeteoClient.getOpenMeteoArchiveData(lat, lon, date, date, "weather_code,temperature_2m_min,temperature_2m_max");
        } catch (Exception e) {
            throw new OpenMeteoException(e.getMessage());
        }

        if (response == null || response.daily() == null ||
            response.daily().time().isEmpty() || response.daily().weatherCode().isEmpty()) {
            return Optional.empty();
        }

        LocalDate parsedDate = LocalDate.parse(response.daily().time().getFirst());
        int weatherCode = response.daily().weatherCode().getFirst();
        Double minTemp = response.daily().temperature2mMin().getFirst();
        Double maxTemp = response.daily().temperature2mMax().getFirst();

        return Optional.of(new DailyWeather(parsedDate, weatherCode, minTemp, maxTemp));
    }

}
