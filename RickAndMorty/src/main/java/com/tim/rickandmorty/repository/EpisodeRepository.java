package com.tim.rickandmorty.repository;

import com.tim.rickandmorty.entity.Episode;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface EpisodeRepository extends PagingAndSortingRepository<Episode, Long>, JpaSpecificationExecutor<Episode> {
    Optional<Episode> findByUrl(String url);
}