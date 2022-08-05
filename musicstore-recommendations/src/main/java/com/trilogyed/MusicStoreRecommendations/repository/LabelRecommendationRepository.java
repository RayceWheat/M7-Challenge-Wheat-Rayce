package com.trilogyed.MusicStoreRecommendations.repository;

import com.trilogyed.MusicStoreRecommendations.model.LabelRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LabelRecommendationRepository extends JpaRepository<LabelRecommendation, Integer> {
}
