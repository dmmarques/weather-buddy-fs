package com.algomarques.tb.weather_ms.domain.web;

import com.algomarques.tb.weather_ms.common.exception.ErrorResponse;
import com.algomarques.tb.weather_ms.domain.dto.WeatherResponse;
import com.algomarques.tb.weather_ms.domain.service.IWeatherService;
import com.algomarques.tb.weather_ms.domain.service.WeatherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/weather")
@RequiredArgsConstructor
@Slf4j
public class WeatherController {

    private final IWeatherService weatherHistoricalService;
    private final WeatherService weatherService;

    @Operation(
        operationId = "checkWeatherStatus",
        summary = "Checks the Weather Status for a given latitude, longitude and a date in time",
        tags = { "weather" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Ok", content = {
                @Content(mediaType = "application/json;charset=utf-8", schema = @Schema(implementation = WeatherResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                @Content(mediaType = "application/json;charset=utf-8", schema = @Schema(implementation = ErrorResponse.class))
            }),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
                @Content(mediaType = "application/json;charset=utf-8", schema = @Schema(implementation = ErrorResponse.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
                @Content(mediaType = "application/json;charset=utf-8", schema = @Schema(implementation = ErrorResponse.class))
            })
        }
    )
    @GetMapping(value = "/historical/check", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WeatherResponse> checkHistoricalWeatherStatus(
        @NotNull @RequestParam double latitude,
        @NotNull @RequestParam double longitude,
        @NotNull @RequestParam List<LocalDate> dates) {
        log.info("Received historical weather request ...");
        return ResponseEntity.ok(weatherHistoricalService.checkWeatherStatus(latitude, longitude, dates));
    }

    @Operation(
        operationId = "checkWeatherStatus",
        summary = "Checks the Weather Status for a given latitude, longitude and a date in time",
        tags = { "weather" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Ok", content = {
                @Content(mediaType = "application/json;charset=utf-8", schema = @Schema(implementation = WeatherResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                @Content(mediaType = "application/json;charset=utf-8", schema = @Schema(implementation = ErrorResponse.class))
            }),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
                @Content(mediaType = "application/json;charset=utf-8", schema = @Schema(implementation = ErrorResponse.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
                @Content(mediaType = "application/json;charset=utf-8", schema = @Schema(implementation = ErrorResponse.class))
            })
        }
    )
    @GetMapping(value = "/check", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WeatherResponse> checkWeatherForecast(
        @NotNull @RequestParam double latitude,
        @NotNull @RequestParam double longitude,
        @NotNull @RequestParam List<LocalDate> dates) {
        log.info("Received weather request ...");
        return ResponseEntity.ok(weatherService.checkWeatherStatus(latitude, longitude, dates));
    }
}
