package com.tim.rickandmorty.tests;

import com.tim.rickandmorty.controller.EpisodeController;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EpisodeControllerTest {
    @Autowired
private MockMvc mockMvc;
    @Autowired
    EpisodeController episodeController;

    @Test
    public void checkIfEpisodeControllerIsNotNull()  {
        assertThat(episodeController).isNotNull();
    }

    @Test
    public void checkEpisodeControllerOn2xxStatus() throws Exception {
        mockMvc.perform(get("/episode")).andExpect(status().isOk());
    }
}