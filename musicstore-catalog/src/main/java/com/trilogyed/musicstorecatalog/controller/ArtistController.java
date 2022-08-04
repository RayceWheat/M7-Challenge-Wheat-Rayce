package com.trilogyed.musicstorecatalog.controller;

import com.trilogyed.musicstorecatalog.model.Artist;
import com.trilogyed.musicstorecatalog.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/artist")
public class ArtistController {
    
    @Autowired
    ArtistRepository repo;


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Artist> getAllArtists(){
        List<Artist> allArtists = repo.findAll();
        if (allArtists == null || allArtists.isEmpty()){
            throw new IllegalArgumentException("No artists were found");
        } else {
            return allArtists;
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Artist createArtist(@RequestBody @Valid Artist artist){
        repo.save(artist);
        return artist;
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateArtist(@RequestBody @Valid Artist artist){
        if (artist == null || artist.getId() < 1){
            throw new IllegalArgumentException("Artist does not exists with this Id");
        } else if (artist.getId() > 0){
            repo.save(artist);
        }
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Artist getArtistById(@PathVariable("id") long artistId){
        Optional<Artist> artist = repo.findById(artistId);
        if (artist == null){
            throw new IllegalArgumentException("Artist not found with this id");
        } else {
            return (artist.get());
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteArtistById(@PathVariable("id") long artistId){
        repo.deleteById(artistId);
    }


}
