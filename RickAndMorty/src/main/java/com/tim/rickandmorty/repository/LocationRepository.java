package com.tim.rickandmorty.repository;

import com.tim.rickandmorty.entity.Location;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface LocationRepository extends CrudRepository<Location, Long> {
    Optional<Location> findByName(String name);

    List<Location> findAll();
    }