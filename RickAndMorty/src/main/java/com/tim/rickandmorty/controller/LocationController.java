package com.tim.rickandmorty.controller;

import com.tim.rickandmorty.response.LocationResponse;
import com.tim.rickandmorty.service.LocationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/location")
public class LocationController {

    private final LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
        log.info("Autowired:", LocationService.class.getName(), LocationController.class.getName());
    }

    @GetMapping()
    public List<LocationResponse> getAllLocations() {
        List<LocationResponse> locationResponse = null;
            locationResponse = locationService.getAllLocations();
            log.info("get all locations", LocationController.class.getName());
            return locationResponse;
    }

    @GetMapping("/{ids}")
    public List<LocationResponse> getLocationsByArrayOfIds(@PathVariable List<String> ids) {
        List<LocationResponse> locationResponses = null;
            locationResponses = locationService.getLocationsByIds(ids);
            log.info("get locations by ids", ids);
            return locationResponses;
    }
}