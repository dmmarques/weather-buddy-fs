package com.algomarques.tb.weather_ms.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_GATEWAY)
public class OpenMeteoException extends RuntimeException {

    public OpenMeteoException(String message) {
        super(message);
    }

}
