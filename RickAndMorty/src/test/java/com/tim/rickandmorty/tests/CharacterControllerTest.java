package com.tim.rickandmorty.tests;

import com.tim.rickandmorty.controller.CharacterController;
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
public class CharacterControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    CharacterController characterController;

    @Test
    public void checkIfCharacterControllerIsNotNull()  {
        assertThat(characterController).isNotNull();
    }

    @Test
    public void checkCharacterControllerOn2xxStatus() throws Exception {
        mockMvc.perform(get("/character")).andExpect(status().isOk());
    }
}