package com.algomarques.tb.weather_ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class App {

	/*
		TODO:
			- Add the mapping between WeatherCodes and its Real World values
				- DailyWeather.java
				- Review and check if there's a simpler way to do this
			- Check coverage and meaningfulness of the unit tests
			- Read all Codes and Values from the jsonFile
				- Check if this is a good approach
	 */

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

}
