package com.tim.rickandmorty.service;

import com.tim.rickandmorty.dto.character.CharacterDTO;
import com.tim.rickandmorty.dto.character.PageCharacter;
import com.tim.rickandmorty.entity.Characters;
import com.tim.rickandmorty.entity.Episode;
import com.tim.rickandmorty.entity.Location;
import com.tim.rickandmorty.enums.Gender;
import com.tim.rickandmorty.enums.Status;
import com.tim.rickandmorty.repository.CharacterRepository;
import com.tim.rickandmorty.repository.EpisodeRepository;
import com.tim.rickandmorty.repository.LocationRepository;
import com.tim.rickandmorty.response.CharacterResponse;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

import static com.tim.rickandmorty.constant.ProgrammConstant.CHARACTER_URL;

@Service
public class CharacterService {

    private ModelMapper modelMapper = new ModelMapper();

    private CharacterRepository characterRepository;
    private LocationRepository locationRepository;
    private EpisodeRepository episodeRepository;

    public CharacterService(CharacterRepository characterRepository,
                            LocationRepository locationRepository,
                            EpisodeRepository episodeRepository) {
        this.characterRepository = characterRepository;
        this.locationRepository = locationRepository;
        this.episodeRepository = episodeRepository;
    }

    public Characters save(Characters characters) {
        return characterRepository.save(characters);
    }

    @Scheduled(cron = "0 15 * * * *") //every day at 15:00
    public void saveToDatabase(RestTemplate restTemplate) {
        PageCharacter pageCharacter = restTemplate.getForObject(CHARACTER_URL, PageCharacter.class);

        List<PageCharacter> pageCharacterList = new ArrayList<>();
        while (true) {
            pageCharacterList.add(pageCharacter);
            pageCharacter = restTemplate.getForObject(pageCharacter.getInfo().getNext(), PageCharacter.class);
            if (pageCharacter.getInfo().getNext() == null) {
                pageCharacterList.add(pageCharacter);
                break;
            }
        }

        pageCharacterList.forEach(pageCharacterElement -> {
            List<CharacterDTO> results = pageCharacterElement.getResults();
            results.forEach(result -> {
                Characters characters = modelMapper.map(result, Characters.class);

                characters.setStatus(Status.valueOf(result.getStatus().toUpperCase(Locale.ROOT)));
                characters.setGender(Gender.valueOf(result.getGender().toUpperCase(Locale.ROOT)));

                Optional<Location> location = locationRepository.findByName(characters.getLocation().getName());
                Optional<Location> origin = locationRepository.findByName(characters.getOrigin().getName());

                if (!origin.isPresent()) {
                    characters.setOrigin(null);
                }
                if (!location.isPresent()) {
                    characters.setLocation(null);
                }
                origin.ifPresent(characters::setOrigin);
                location.ifPresent(characters::setLocation);

                List<Episode> episodeList = new ArrayList<>();
                result.getEpisode().forEach(episode -> {
                    Optional<Episode> optionalEpisode = episodeRepository.findByUrl(episode);
                    optionalEpisode.ifPresent(episodeList::add);
                });
                characters.setEpisode(episodeList);
                save(characters);
            });
        });
    }
    public List<CharacterResponse> getAllCharacters() {
        Iterable<Characters> chars = characterRepository.findAll();
        //достать всех, пройтись по каждому чару и сделать из них CharacterResponse

        List<CharacterResponse> characterResponse = new ArrayList<>();
        Iterator<Characters> iterator = chars.iterator();
        while (iterator.hasNext()) {
            Characters element = iterator.next();
            characterResponse.add(map(element));
        }
        return characterResponse;
    }

    CharacterResponse map(Characters char1) {
        ModelMapper modelMapper = new ModelMapper();
        CharacterResponse map = modelMapper.map(char1, CharacterResponse.class);
        return map;
    }

//        return LongStream.iterate(1, i -> i + 1)
//               .mapToObj(id -> characterRepository.findAll().orElseGet(null))
//              .filter(Objects::nonNull)
//                .limit(826)
//                .map(character -> modelMapper.map(character, CharacterResponse.class))
//                .collect(Collectors.toList());
//    }

    public List<CharacterResponse> getCharactersByIds(List<String> ids) {
        return ids.stream()
                .map(id -> characterRepository.findById(Long.valueOf(id)).orElseGet(null))
                .filter(Objects::nonNull)
                .map(character -> modelMapper.map(character, CharacterResponse.class))
                .collect(Collectors.toList());
    }
}