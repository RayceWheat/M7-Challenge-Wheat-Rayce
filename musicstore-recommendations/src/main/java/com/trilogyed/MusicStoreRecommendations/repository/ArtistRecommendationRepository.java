package com.trilogyed.MusicStoreRecommendations.repository;


import com.trilogyed.MusicStoreRecommendations.model.ArtistRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistRecommendationRepository extends JpaRepository<ArtistRecommendation, Integer> {
}
