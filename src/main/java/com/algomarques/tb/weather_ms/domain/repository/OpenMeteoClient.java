package com.algomarques.tb.weather_ms.domain.repository;

import com.algomarques.tb.weather_ms.domain.dto.OpenMeteoResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@FeignClient(name = "openmeteo-client", url = "${provider.openmeteo.forecast.v1}")
public interface OpenMeteoClient {

    @GetMapping("/forecast")
    OpenMeteoResponse getOpenMeteoData(@NotNull @RequestParam double latitude, @NotNull @RequestParam double longitude,
        @NotNull @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start_date,
        @NotNull @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end_date,
        @NotNull @RequestParam String daily);

}
