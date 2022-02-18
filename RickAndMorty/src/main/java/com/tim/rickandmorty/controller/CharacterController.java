package com.tim.rickandmorty.controller;


import com.tim.rickandmorty.response.CharacterResponse;
import com.tim.rickandmorty.service.CharacterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/character")
public class CharacterController {
    private final CharacterService characterService;

    @Autowired
    public CharacterController(CharacterService characterService) {
        this.characterService = characterService;
        log.info("Autowired: ", CharacterService.class.getName(), CharacterController.class.getName());
    }

    @GetMapping()
    public List<CharacterResponse> getAllCharacters() {
        List<CharacterResponse> characterResponse = null;
            characterResponse = characterService.getAllCharacters();
            log.info("getting all characters", CharacterController.class.getName());
            return characterResponse;
    }

    @GetMapping("/{ids}")
    public List<CharacterResponse> getCharactersByIds(@PathVariable List<String> ids) {
        List<CharacterResponse> characterResponses = null;
            characterResponses = characterService.getCharactersByIds(ids);
            log.info("getting characters by ids", ids);
        return characterResponses;
    }
}