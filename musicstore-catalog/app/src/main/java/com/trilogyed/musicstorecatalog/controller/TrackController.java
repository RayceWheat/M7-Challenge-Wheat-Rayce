package com.trilogyed.musicstorecatalog.controller;

import com.trilogyed.musicstorecatalog.model.Track;
import com.trilogyed.musicstorecatalog.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/track")
public class TrackController {
    
    @Autowired
    TrackRepository repo;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Track> getAllTracks(){
        List<Track> allTracks = repo.findAll();
        if (allTracks == null || allTracks.isEmpty()){
            throw new IllegalArgumentException("No tracks were found");
        } else {
            return allTracks;
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Track createTrack(@RequestBody @Valid Track track){
        repo.save(track);
        return track;
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTrack(@RequestBody @Valid Track track){
        if (track == null || track.getId() < 1){
            throw new IllegalArgumentException("Track does not exists with this Id");
        } else if (track.getId() > 0){
            repo.save(track);
        }
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Track getTrackById(@PathVariable("id") Integer trackId){
        Optional<Track> track = repo.findById(trackId);
        if (track == null){
            throw new IllegalArgumentException("Track not found with this id");
        } else {
            return (track.get());
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTrackById(@PathVariable("id") Integer trackId){
        repo.deleteById(trackId);
    }


}
