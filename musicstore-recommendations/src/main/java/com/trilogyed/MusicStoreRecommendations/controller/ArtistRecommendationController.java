package com.trilogyed.MusicStoreRecommendations.controller;

import com.trilogyed.MusicStoreRecommendations.model.ArtistRecommendation;
import com.trilogyed.MusicStoreRecommendations.repository.AlbumRecommendationRepository;
import com.trilogyed.MusicStoreRecommendations.repository.ArtistRecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/artistRec")
public class ArtistRecommendationController {

    @Autowired
    ArtistRecommendationRepository repo;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ArtistRecommendation> getAllArtistRecommendations(){
        List<ArtistRecommendation> allArtistRecommendations = repo.findAll();
        if (allArtistRecommendations == null || allArtistRecommendations.isEmpty()){
            throw new IllegalArgumentException("No artistRecs were found");
        } else {
            return allArtistRecommendations;
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ArtistRecommendation createArtistRecommendation(@RequestBody @Valid ArtistRecommendation artistRec){
        repo.save(artistRec);
        return artistRec;
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateArtistRecommendation(@RequestBody @Valid ArtistRecommendation artistRec){
        if (artistRec == null || artistRec.getId() < 1){
            throw new IllegalArgumentException("ArtistRecommendation does not exists with this Id");
        } else if (artistRec.getId() > 0){
            repo.save(artistRec);
        }
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ArtistRecommendation getArtistRecommendationById(@PathVariable("id") Integer artistRecId){
        Optional<ArtistRecommendation> artistRec = repo.findById(artistRecId);
        if (artistRec == null){
            throw new IllegalArgumentException("ArtistRecommendation not found with this id");
        } else {
            return (artistRec.get());
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteArtistRecommendationById(@PathVariable("id") Integer artistRecId){
        repo.deleteById(artistRecId);
    }



}
