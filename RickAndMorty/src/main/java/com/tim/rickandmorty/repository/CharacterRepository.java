package com.tim.rickandmorty.repository;

import com.tim.rickandmorty.entity.Characters;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterRepository extends CrudRepository<Characters, Long> {
}
