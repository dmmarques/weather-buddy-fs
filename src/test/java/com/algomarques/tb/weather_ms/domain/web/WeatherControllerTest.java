package com.algomarques.tb.weather_ms.domain.web;

import com.algomarques.tb.weather_ms.common.exception.OpenMeteoException;
import com.algomarques.tb.weather_ms.domain.dto.WeatherResponse;
import com.algomarques.tb.weather_ms.domain.service.WeatherHistoricalService;
import com.algomarques.tb.weather_ms.domain.service.WeatherService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = WeatherController.class)
class WeatherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private WeatherHistoricalService weatherHistoricalService;

    @MockitoBean
    private WeatherService weatherService;

    @Test
    @DisplayName("when controlled is called, should return 200")
    void test1() throws Exception {
        WeatherResponse weatherResponse = WeatherResponse.init().build();
        Mockito.when(weatherService.checkWeatherStatus(Mockito.anyDouble(), Mockito.anyDouble(), Mockito.anyList())).thenReturn(weatherResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/check")
                                              .param("latitude", "0.2")
                                              .param("longitude", "0.3")
                                              .param("dates", "1996-04-16"))
               .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("when controlled is called without latitude, should return 400")
    void test2() throws Exception {
        WeatherResponse weatherResponse = WeatherResponse.init().build();
        Mockito.when(weatherService.checkWeatherStatus(Mockito.anyDouble(), Mockito.anyDouble(), Mockito.anyList())).thenReturn(weatherResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/check")
                                              .param("longitude", "0.3")
                                              .param("dates", "1996-04-16"))
               .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("when controlled is called without longitude, should return 400")
    void test3() throws Exception {
        WeatherResponse weatherResponse = WeatherResponse.init().build();
        Mockito.when(weatherService.checkWeatherStatus(Mockito.anyDouble(), Mockito.anyDouble(), Mockito.anyList())).thenReturn(weatherResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/check")
                                              .param("latitude", "0.3")
                                              .param("dates", "1996-04-16"))
               .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("when controlled is called without dates, should return 400")
    void test4() throws Exception {
        WeatherResponse weatherResponse = WeatherResponse.init().build();
        Mockito.when(weatherService.checkWeatherStatus(Mockito.anyDouble(), Mockito.anyDouble(), Mockito.anyList())).thenReturn(weatherResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/check")
                                              .param("latitude", "0.2")
                                              .param("longitude", "0.3"))
               .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("when controlled is called without parameters, should return 400")
    void test5() throws Exception {
        WeatherResponse weatherResponse = WeatherResponse.init().build();
        Mockito.when(weatherService.checkWeatherStatus(Mockito.anyDouble(), Mockito.anyDouble(), Mockito.anyList())).thenReturn(weatherResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/check"))
               .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("when controlled is called in an unknown endpoint, should return 404")
    void test6() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/check/nonexistent"))
               .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("when controlled is called and an error on external call occurs, should return 502")
    void test7() throws Exception {
        Mockito.doThrow(OpenMeteoException.class)
               .when(weatherService)
               .checkWeatherStatus(Mockito.anyDouble(), Mockito.anyDouble(), Mockito.anyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/check")
                                              .param("latitude", "0.2")
                                              .param("longitude", "0.3")
                                              .param("dates", "1996-04-16"))
               .andExpect(MockMvcResultMatchers.status().isBadGateway());
    }

}