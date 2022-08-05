package com.trilogyed.MusicStoreRecommendations.controller;

import com.trilogyed.MusicStoreRecommendations.model.AlbumRecommendation;
import com.trilogyed.MusicStoreRecommendations.repository.AlbumRecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/albumRec")
public class AlbumRecommendationController {

    @Autowired
    AlbumRecommendationRepository repo;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AlbumRecommendation> getAllAlbumRecommendations(){
        List<AlbumRecommendation> allAlbumRecommendations = repo.findAll();
        if (allAlbumRecommendations == null || allAlbumRecommendations.isEmpty()){
            throw new IllegalArgumentException("No albumRecs were found");
        } else {
            return allAlbumRecommendations;
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AlbumRecommendation createAlbumRecommendation(@RequestBody @Valid AlbumRecommendation albumRec){
        repo.save(albumRec);
        return albumRec;
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAlbumRecommendation(@RequestBody @Valid AlbumRecommendation albumRec){
        if (albumRec == null || albumRec.getId() < 1){
            throw new IllegalArgumentException("AlbumRecommendation does not exists with this Id");
        } else if (albumRec.getId() > 0){
            repo.save(albumRec);
        }
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AlbumRecommendation getAlbumRecommendationById(@PathVariable("id") Integer albumRecId){
        Optional<AlbumRecommendation> albumRec = repo.findById(albumRecId);
        if (albumRec == null){
            throw new IllegalArgumentException("AlbumRecommendation not found with this id");
        } else {
            return (albumRec.get());
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAlbumRecommendationById(@PathVariable("id") Integer albumRecId){
        repo.deleteById(albumRecId);
    }



}
