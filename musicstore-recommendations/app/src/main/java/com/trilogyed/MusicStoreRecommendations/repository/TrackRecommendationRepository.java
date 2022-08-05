package com.trilogyed.MusicStoreRecommendations.repository;

import com.trilogyed.MusicStoreRecommendations.model.TrackRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackRecommendationRepository extends JpaRepository<TrackRecommendation, Integer> {
}
