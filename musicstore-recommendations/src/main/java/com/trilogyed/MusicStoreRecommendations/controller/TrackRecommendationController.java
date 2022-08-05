package com.trilogyed.MusicStoreRecommendations.controller;

import com.trilogyed.MusicStoreRecommendations.model.TrackRecommendation;
import com.trilogyed.MusicStoreRecommendations.repository.TrackRecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/trackRec")
public class TrackRecommendationController {

    @Autowired
    TrackRecommendationRepository repo;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TrackRecommendation> getAllTrackRecommendations(){
        List<TrackRecommendation> allTrackRecommendations = repo.findAll();
        if (allTrackRecommendations == null || allTrackRecommendations.isEmpty()){
            throw new IllegalArgumentException("No trackRecs were found");
        } else {
            return allTrackRecommendations;
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TrackRecommendation createTrackRecommendation(@RequestBody @Valid TrackRecommendation trackRec){
        repo.save(trackRec);
        return trackRec;
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTrackRecommendation(@RequestBody @Valid TrackRecommendation trackRec){
        if (trackRec == null || trackRec.getId() < 1){
            throw new IllegalArgumentException("TrackRecommendation does not exists with this Id");
        } else if (trackRec.getId() > 0){
            repo.save(trackRec);
        }
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TrackRecommendation getTrackRecommendationById(@PathVariable("id") Integer trackRecId){
        Optional<TrackRecommendation> trackRec = repo.findById(trackRecId);
        if (trackRec == null){
            throw new IllegalArgumentException("TrackRecommendation not found with this id");
        } else {
            return (trackRec.get());
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTrackRecommendationById(@PathVariable("id") Integer trackRecId){
        repo.deleteById(trackRecId);
    }


}
