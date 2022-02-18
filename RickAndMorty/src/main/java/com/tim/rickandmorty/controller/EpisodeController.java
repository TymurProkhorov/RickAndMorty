package com.tim.rickandmorty.controller;

import com.tim.rickandmorty.response.EpisodeResponse;
import com.tim.rickandmorty.service.CharacterService;
import com.tim.rickandmorty.service.EpisodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/episode")
public class EpisodeController {

    private final EpisodeService episodeService;

    @Autowired
    public EpisodeController(EpisodeService episodeService) {
        this.episodeService = episodeService;
        log.info("Autowired: ", EpisodeService.class.getName(), EpisodeController.class.getName());
    }

    @GetMapping()
    public List<EpisodeResponse> getAllEpisodes() {
        List<EpisodeResponse> episodeResponse = null;
            episodeResponse = episodeService.getAllEpisodes();
            log.info("get all locations", EpisodeController.class.getName());
            return episodeResponse;
    }

    @GetMapping("/{ids}")
    public List<EpisodeResponse> getEpisodesByIds(@PathVariable List<String> ids) {
        List<EpisodeResponse> episodeResponses  = null;
            episodeResponses = episodeService.getEpisodesByIds(ids);
            log.info("get episodes by ids", ids);
            return episodeResponses;

    }
}