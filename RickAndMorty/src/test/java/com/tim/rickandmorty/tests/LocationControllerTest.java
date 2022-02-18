package com.tim.rickandmorty.tests;

import com.tim.rickandmorty.controller.LocationController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class LocationControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    LocationController locationController;

    @Test
    public void checkIfLocationControllerIsNotNull() {
        assertThat(locationController).isNotNull();
    }

    @Test
    public void checkLocationControllerOn2xxStatus() throws Exception {
        mockMvc.perform(get("/location")).andExpect(status().isOk());
    }
}