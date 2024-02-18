package com.example.weather;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class WeatherApplicationTests {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetWeatherByCity() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/weather")
                        .param("city", "London"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(org.hamcrest.Matchers.containsString("прогноз погоды")));
    }

    @Test
    public void testGetWeatherByCoordinates() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/weatherByCoordinates")
                        .param("lat", "51.5074")
                        .param("lon", "0.1278"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(org.hamcrest.Matchers.containsString("прогноз погоды")));
    }
}

