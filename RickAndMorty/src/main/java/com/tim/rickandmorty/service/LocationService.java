package com.tim.rickandmorty.service;

import com.tim.rickandmorty.dto.location.LocationDTO;
import com.tim.rickandmorty.dto.location.PageLocation;
import com.tim.rickandmorty.entity.Characters;
import com.tim.rickandmorty.entity.Location;
import com.tim.rickandmorty.repository.LocationRepository;
import com.tim.rickandmorty.response.CharacterResponse;
import com.tim.rickandmorty.response.LocationResponse;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static com.tim.rickandmorty.constant.ProgrammConstant.LOCATION_URL;

@Service
public class LocationService {

    private ModelMapper modelMapper = new ModelMapper();
    private LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public Iterable<Location> list() {
        return locationRepository.findAll();
    }

    public Location save(Location location) {
        return locationRepository.save(location);
    }

    public void save(List<Location> locations) {
        locationRepository.saveAll(locations);
    }

    @Scheduled(cron = "0 15 * * * *") //every day at 15:00
    public void saveToDatabase(RestTemplate restTemplate) {
        PageLocation pageLocation = restTemplate.getForObject(LOCATION_URL, PageLocation.class);
        List<PageLocation> pageLocationList = new ArrayList<>();

        while (true) {
            pageLocationList.add(pageLocation);
            pageLocation = restTemplate.getForObject(pageLocation.getInfo().getNext(), PageLocation.class);
            if (pageLocation.getInfo().getNext() == null) {
                pageLocationList.add(pageLocation);
                break;
            }
        }

        ArrayList<Location> locations = new ArrayList<>();
        pageLocationList.forEach(pageLocationElement -> {
            List<LocationDTO> results = pageLocationElement.getResults();
            results.forEach(result -> {
                Location location = modelMapper.map(result, Location.class);
                locations.add(location);
            });
        });
        save(locations);
    }

    @Cacheable("locations")
    public List<LocationResponse> getAllLocations() {
        Iterable<Location> locations = locationRepository.findAll();
        //достать всех, пройтись по каждому чару и сделать из них CharacterResponse

        List<LocationResponse> locationResponse = new ArrayList<>();
        Iterator<Location> iterator = locations.iterator();
        while (iterator.hasNext()) {
            Location element = iterator.next();
            locationResponse.add(map(element));
        }
        return locationResponse;
    }

    LocationResponse map(Location loc1) {
        ModelMapper modelMapper = new ModelMapper();
        LocationResponse map = modelMapper.map(loc1, LocationResponse.class);
        return map;
    }


    @Cacheable("locations")
    public List<LocationResponse> getLocationsByIds(List<String> ids) {
        System.out.println("get location by id");
        return ids.stream()
                .map(id -> locationRepository.findById(Long.valueOf(id)).orElseGet(null))
                .filter(Objects::nonNull)
                .map(location -> modelMapper.map(location, LocationResponse.class))
                .collect(Collectors.toList());
    }
}