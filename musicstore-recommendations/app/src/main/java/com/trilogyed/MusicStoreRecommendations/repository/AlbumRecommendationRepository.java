package com.trilogyed.MusicStoreRecommendations.repository;

import com.trilogyed.MusicStoreRecommendations.model.AlbumRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumRecommendationRepository extends JpaRepository<AlbumRecommendation, Integer> {
}
