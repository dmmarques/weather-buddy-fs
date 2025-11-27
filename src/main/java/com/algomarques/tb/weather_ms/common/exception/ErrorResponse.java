package com.algomarques.tb.weather_ms.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class ErrorResponse {

    private int code;
    private String message;
    private LocalDateTime timestamp;

}
