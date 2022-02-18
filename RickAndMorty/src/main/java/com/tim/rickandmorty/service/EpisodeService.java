package com.tim.rickandmorty.service;


import com.tim.rickandmorty.dto.episode.EpisodeDTO;
import com.tim.rickandmorty.dto.episode.PageEpisode;
import com.tim.rickandmorty.entity.Episode;
import com.tim.rickandmorty.repository.EpisodeRepository;
import com.tim.rickandmorty.response.EpisodeResponse;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static com.tim.rickandmorty.constant.ProgrammConstant.EPISODE_URL;

@Service
public class EpisodeService {

    private ModelMapper modelMapper = new ModelMapper();

    private EpisodeRepository episodeRepository;

    public List<Episode> list() {
        List<Episode> episodes = new ArrayList<>();
        for (Episode episode : episodeRepository.findAll()) {
            episodes.add(episode);
        }

        return episodes;
    }

    public EpisodeService(EpisodeRepository episodeRepository) {
        this.episodeRepository = episodeRepository;
    }

    public Episode save(Episode episode) {
        return episodeRepository.save(episode);
    }

    public void save(List<Episode> episodes) {
        episodeRepository.saveAll(episodes);
    }

    @Scheduled(cron = "0 15 * * * *") //every day at 15:00
    public void saveToDatabase(RestTemplate restTemplate) {
        PageEpisode pageEpisode = restTemplate.getForObject(EPISODE_URL, PageEpisode.class);

        List<PageEpisode> pageEpisodeList = new ArrayList<>();
        while (true) {
            pageEpisodeList.add(pageEpisode);
            pageEpisode = restTemplate.getForObject(pageEpisode.getInfo().getNext(), PageEpisode.class);
            if (pageEpisode.getInfo().getNext() == null) {
                pageEpisodeList.add(pageEpisode);
                break;
            }
        }

        ArrayList<Episode> episodes = new ArrayList<>();
        pageEpisodeList.forEach(pageEpisodeElement -> {
            List<EpisodeDTO> results = pageEpisodeElement.getResults();
            results.forEach(result -> {
                Episode episode = modelMapper.map(result, Episode.class);
                episodes.add(episode);
            });
        });
        save(episodes);
    }

    public List<EpisodeResponse> getAllEpisodes() {
Iterable<Episode> episodes = episodeRepository.findAll();
List<EpisodeResponse> episodeResponse = new ArrayList<>();
Iterator<Episode> iterator = episodes.iterator();
while(iterator.hasNext()) {
    Episode element = iterator.next();
    episodeResponse.add(map(element));
}
return episodeResponse;
    }

    EpisodeResponse map(Episode ep1) {
        ModelMapper modelMapper = new ModelMapper();
        EpisodeResponse map = modelMapper.map(ep1,EpisodeResponse.class);
        return map;
    }

    public List<EpisodeResponse> getEpisodesByIds(List<String> ids) {
        return ids.stream()
                .map(id -> episodeRepository.findById(Long.valueOf(id)).orElseGet(null))
                .filter(Objects::nonNull)
                .map(episode -> modelMapper.map(episode, EpisodeResponse.class))
                .collect(Collectors.toList());
    }
}