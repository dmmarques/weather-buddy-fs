package com.algomarques.tb.weather_ms.domain.repository;

import com.algomarques.tb.weather_ms.domain.dto.OpenMeteoResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@FeignClient(name = "openmeteo-hostorical-client", url = "${provider.openmeteo.endpoint.v1}")
public interface OpenMeteoHistoricalClient {

    @GetMapping("/archive")
    OpenMeteoResponse getOpenMeteoArchiveData(@NotNull @RequestParam double latitude, @NotNull @RequestParam double longitude,
        @NotNull @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start_date,
        @NotNull @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end_date,
        @NotNull @RequestParam String daily);

}
